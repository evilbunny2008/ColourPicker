package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class CustomEditText extends TextInputEditText
{
	private OnColourPickedListener listener;
	private char fixedChar = '#';

	public CustomEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs, 0);
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	public void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		setOnClickListener(this::handleClick);

		if(Common.isEmpty(getText()))
		{
			setText(String.valueOf(fixedChar));
			setSelection(1); // cursor after fixed char
		}

		addTextChangedListener(new TextWatcher()
		{
			boolean isUpdating = false;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if(isUpdating)
					return;

				isUpdating = true;

				// Ensure the first character is always the fixed char
				if(s.length() == 0)
					s.insert(0, String.valueOf(fixedChar));
				else if(s.charAt(0) != fixedChar)
					s.insert(0, String.valueOf(fixedChar));

				// Prevent cursor from going before fixed char
				if(getSelectionStart() == 0)
					setSelection(1);

				isUpdating = false;
			}
		});
	}

	public void setOnColourPickedListener(OnColourPickedListener listener)
	{
		this.listener = listener;
	}

	@Override
	public void setText(CharSequence text, BufferType type)
	{
		String input = Common.to_ARGB_hex(text.toString());
		Common.LogMessage("line100 input.length() = " + input.length());
		Common.LogMessage("input padded = " + input);
		super.setText(input, type);
	}

	/** Optional: allow changing the fixed character */
	public void setFixedChar(char c)
	{
		this.fixedChar = c;
		Editable text = getText();
		if (text != null && text.length() > 0)
			text.replace(0, 1, String.valueOf(fixedChar));
		else
			setText(String.valueOf(fixedChar));

		setSelection(1);
	}

	/** Returns the text without the fixed char */
	public String getUserText()
	{
		Editable text = getText();
		if(text == null || text.length() <= 1)
			return "";

		return text.subSequence(1, text.length()).toString();
	}

	public interface OnColourPickedListener
	{
		void onColourPicked(int colour, boolean isForeground);
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