package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;


import static com.github.evilbunny2008.colourpicker.ColourPickerCommon.LogMessage;
import static com.github.evilbunny2008.colourpicker.ColourPickerCommon.getActivity;
import static com.github.evilbunny2008.colourpicker.ColourPickerCommon.parseHexToColour;
import static com.github.evilbunny2008.colourpicker.ColourPickerCommon.to_ARGB_hex;

@SuppressWarnings({"unused"})
public class CPEditText extends CustomEditText implements CustomEditText.OnClickListener
{
	private static int colour;
	private ColourPicker cp;

	public CPEditText(Context context)
	{
		super(context);
		init(context, null, 0);
	}

	public CPEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs, 0);
	}

	public CPEditText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	public void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super.init(context, attrs, defStyleAttr);
		setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		LogMessage("CPEditText getText() = " + getText());
		String colour;
		Editable text = getText();
		LogMessage("text.toString() = " + text);
		if(text == null || text.length() == 0)
			colour = getFixedChar() + "FFFFFFFF";
		else
			colour = getFixedChar() + text.toString().replaceAll(String.valueOf(getFixedChar()), "").trim();

		LogMessage("CPEditText.onClick() Line 47 Colour: " + colour);

		String hex = to_ARGB_hex(colour);
		LogMessage("CPEditText.onClick() Line 50 Hex: " + hex);

		Activity activity = getActivity(view.getContext());
		if(cp == null)
		{
			cp = new ColourPicker(activity, parseHexToColour(colour));

			cp.setCallback(newColour ->
			{
				LogMessage("cp.setCallback() Line 53 Pure Hex: " + newColour);
				setText(newColour);
				cp.dismiss();
			});

			cp.setTextCallback(newColour ->
			{
				LogMessage("cp.setTextCallback() Line 60 Pure Hex: " + newColour);
				setText(newColour);
				cp.dismiss();
			});
		} else {
			cp.setAll(parseHexToColour(colour));
		}

		cp.show();
	}
}