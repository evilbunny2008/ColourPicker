package com.github.evilbunny2008.ColourPicker;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class CPEditText extends CustomEditText
{
	private OnColourPickedListener listener;

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
		setOnClickListener(this::handleClick);
	}

	public interface OnColourPickedListener
	{
		void onColourPicked(int colour, boolean isForeground);
	}

	public void setOnColourPickedListener(OnColourPickedListener listener)
	{
		this.listener = listener;
	}

	private void handleClick(View v)
	{
		Activity activity = Common.getActivity(v.getContext());
		int colour = Common.parseHexToColour(getText() != null ? getText().toString() : "#FFFFFFFF");
		ColourPicker cp = new ColourPicker(activity,
				(colour >> 24) & 0xFF,
				(colour >> 16) & 0xFF,
				(colour >> 8) & 0xFF,
				colour & 0xFF);

		cp.setCallback(newColour ->
		{
			String hex = Common.to_ARGB_hex(String.format("%8X", newColour));
			Common.LogMessage("Pure Hex: " + hex);
			setText(hex);

			if(listener != null)
				listener.onColourPicked(newColour, true);

			cp.dismiss();
		});

		cp.show();
	}
}
