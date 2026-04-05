package com.github.evilbunny2008.colourpicker;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;


import static com.github.evilbunny2008.colourpicker.ColourPickerCommon.LogMessage;

@SuppressWarnings({"unused"})
public class CustomEditText extends TextInputEditText
{
	private boolean isUpdating = false;
	private static final char fixedChar = '#';

	public CustomEditText(Context context)
	{
		super(context);
		init(context, null, 0);
	}

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
		moveSelector();

		setFilters(new InputFilter[] {(source, start, end, dest, dstart, dend) ->
		{
			LogMessage("InputFilter Line 70 source = " + source);
			LogMessage("InputFilter Line 71 start = " + start);
			LogMessage("InputFilter Line 72 end = " + end);
			LogMessage("InputFilter Line 73 dest = " + dest);
			LogMessage("InputFilter Line 74 dstart = " + dstart);
			LogMessage("InputFilter Line 75 dend = " + dend);

			if(dstart >= 9 && dend >= 9)
				return "";

			String input = String.valueOf(dest.subSequence(0, dstart)) +
					source.subSequence(start, end) +
					dest.subSequence(dend, dest.length());
			LogMessage("InputFilter Line 73 input = " + input);
			String output = filterString(input);
			LogMessage("InputFilter Line 75 output = " + output);

			if(output.equals(input))
				return null;

			if(dstart == dend)
			{
				LogMessage("InputFilter Line 82 new output.substring() = " + output.substring(dstart, dend + 1));
				return output.substring(dstart, dend + 1);
			}

			if(dstart < dend)
			{
				LogMessage("InputFilter Line 88 new output.substring() = " + output.substring(dstart, dend));
				return output.substring(dstart, dend);
			}

			LogMessage("InputFilter Line 92 new output = " + output);
			return output;
		}});

		setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

		addTextChangedListener(new TextWatcher()
		{
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
				LogMessage("afterTextChanged Line 156 s = " + s);

				if(isUpdating)
					return;

				isUpdating = true;

				String str = s.toString();
				LogMessage("afterTextChanged Line 164 str = " + str);

				str = str.replaceAll(String.valueOf(fixedChar), "");
				LogMessage("afterTextChanged Line 167 str = " + str);
				str = fixedChar + str;

				LogMessage("afterTextChanged Line 170 str = " + str);

				if(str.length() > 9)
					str = str.substring(0, 9);

				LogMessage("afterTextChanged Line 175 str = " + str);

				s.replace(0, s.length(), str);

				LogMessage("afterTextChanged Line 179 s = " + s);

				isUpdating = false;

				moveSelector();
			}
		});
	}

	private void moveSelector()
	{
		if(isUpdating)
			return;

		isUpdating = true;

		if(getText() == null || getText().length() == 0)
			setText("#");

		if(getSelectionStart() == 0)
			setSelection(1);

		isUpdating = false;
	}

	@Override
	public void setSelection(int index)
	{
		if(index == 0)
			index = 1;

		super.setSelection(index);
	}

	@Override
	public void setSelection(int start, int stop)
	{
		if(start == 0)
			start = 1;

		if(stop == 0)
			stop = 1;

		super.setSelection(start, stop);
	}

	@Override
	public void setText(CharSequence text, BufferType type)
	{
		String input = text.toString();
		input = filterString(input);
		LogMessage("CustomEditText Line 189 input.length() = " + input.length());
		LogMessage("CustomEditText Line 190 input padded = " + input);
		super.setText(input, type);
	}

	public String filterString(String input)
	{
		if(input == null || input.isEmpty())
			return fixedChar + "FFFFFFFF";

		StringBuilder sb = new StringBuilder();
		sb.append(fixedChar);

		input = input.replaceAll(String.valueOf(fixedChar), "").trim();
		for(int i = 0; i < input.length(); i++)
		{
			char c = Character.toUpperCase(input.charAt(i));
			if(Character.isDigit(c) || (c >= 'A' && c <= 'F'))
				sb.append(c);
		}

		if(sb.length() > 9)
			sb.setLength(9);

		return sb.toString();
	}

	public static char getFixedChar()
	{
		return fixedChar;
	}

	public void setFixedChar(char c)
	{
		int sel = getSelectionStart();
		String str = String.valueOf(getText());
		str = filterString(str);
		setText(str);
		moveSelector();
	}
}