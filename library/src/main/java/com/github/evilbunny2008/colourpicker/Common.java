package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.Editable;
import android.util.Log;

@SuppressWarnings({"unused","SameParameterValue", "FieldCanBeLocal", "CallToPrintStackTrace"})
public class Common
{
	static final private boolean debug_on = false;

	static void doStackOutput(Exception e)
	{
		e.printStackTrace();
	}

	static void LogMessage(String value)
	{
		LogMessage(value, false);
	}

	static void LogMessage(String value, boolean showAnyway)
	{
		if (debug_on || showAnyway)
		{
			int len = value.indexOf("\n");
			if (len <= 0)
				len = value.length();
			Log.i("weeWX Weather", "message='" + value.substring(0, len) + "'");
		}
	}

	public static boolean isEmpty(Editable edit)
	{
		return edit == null || edit.length() == 0;
	}

	public static Activity getActivity(Context context)
	{
		while(context instanceof android.content.ContextWrapper)
		{
			if(context instanceof Activity)
				return (Activity) context;

			context = ((ContextWrapper)context).getBaseContext();
		}

		return null;
	}

	public static int parseHexToColour(String text)
	{
		try
		{
			return (int)Long.parseLong(text.replaceAll(String.valueOf(CustomEditText.getFixedChar()), ""), 16);
		} catch (Exception e) {
			return 0xFFFFFFFF;
		}
	}

	/**
	 * Convert arbitrary hex input into normalized #AARRGGBB string.
	 * Default alpha is FF when missing.
	 */
	public static String to_ARGB_hex(int colour)
	{
		String hex = String.format("#%08X", colour);
		return to_ARGB_hex(hex);
	}

	public static String to_ARGB_hex(String input)
	{
		LogMessage("Common Line70 s = " + input);

		if(input.isEmpty())
			return CustomEditText.getFixedChar() + "FF000000";

		String s = input.replaceAll(String.valueOf(CustomEditText.getFixedChar()), "").trim().toUpperCase();
		s = s.replaceAll("[^0-9A-F]", ""); // keep only hex digits

		LogMessage("Common Line78 s = " + s);

		switch (s.length())
		{
			case 8: // already AARRGGBB
				break;
			case 6: // RRGGBB -> prepend FF alpha
				s = "FF" + s;
				break;
			case 4: // RGBA shorthand (#rgba) -> expand to aarrggbb
				s = expandShorthand4To8(s);
				break;
			case 3: // rgb shorthand -> expand and prepend FF
				s = "FF" + s.charAt(0) + s.charAt(0)
						+ s.charAt(1) + s.charAt(1)
						+ s.charAt(2) + s.charAt(2);
				break;
			default:
				// fallback: pad to 6 then prepend FF
				if (s.length() < 6)
					s = "FF" + String.format("%6s", s).replace(' ', '0');
				else if (s.length() > 8)
					s = s.substring(Math.max(0, s.length() - 8));
				else
					s = String.format("%8s", s).replace(' ', '0');

				break;
		}

		s = CustomEditText.getFixedChar() + s;
		LogMessage("Common Line 108 s = " + s);
		return s;
	}

	/** Helper: expand 4-digit shorthand RGBA into 8-digit AARRGGBB */
	private static String expandShorthand4To8(String s4)
	{
		// s4 length assumed 4, chars: r g b a? But common 4-digit shorthand is RGBA (order RGBA).
		// We want result AARRGGBB where AA from the 4th char repeated.
		char r = s4.charAt(0);
		char g = s4.charAt(1);
		char b = s4.charAt(2);
		char a = s4.charAt(3);

		// expand: RR GG BB -> r r, g g, b b; alpha AA -> a a
		return "" + a + a + r + r + g + g + b + b;
	}
}
