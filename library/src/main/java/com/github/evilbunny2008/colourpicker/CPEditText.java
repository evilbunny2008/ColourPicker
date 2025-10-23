package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import static com.github.evilbunny2008.colourpicker.Common.parseHexToColour;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
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
		Common.LogMessage("CPEditText getText() = " + getText());
		String colour;
		Editable text = getText();
		Common.LogMessage("text.toString() = " + text);
		if(text == null || text.length() == 0)
			colour = getFixedChar() + "FFFFFFFF";
		else
			colour = getFixedChar() + text.toString().replaceAll(String.valueOf(getFixedChar()), "").trim();

		Common.LogMessage("CPEditText.onClick() Line 47 Colour: " + colour);

		String hex = Common.to_ARGB_hex(colour);
		Common.LogMessage("CPEditText.onClick() Line 50 Hex: " + hex);

		Activity activity = Common.getActivity(view.getContext());
		if(cp == null)
		{
			cp = new ColourPicker(activity, parseHexToColour(colour));

			cp.setCallback(newColour ->
			{
				Common.LogMessage("cp.setCallback() Line 53 Pure Hex: " + newColour);
				setText(newColour);
				cp.dismiss();
			});

			cp.setTextCallback(newColour ->
			{
				Common.LogMessage("cp.setTextCallback() Line 60 Pure Hex: " + newColour);
				setText(newColour);
				cp.dismiss();
			});
		} else {
			cp.setAll(parseHexToColour(colour));
		}

		cp.show();
	}
}