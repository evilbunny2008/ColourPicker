package com.github.evilbunny2008.colourpicker;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class CustomEditText extends TextInputEditText
{
	private static char fixedChar = '#';

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
		setFilters(new InputFilter[] {(source, start, end, dest, dstart, dend) ->
		{
			Common.LogMessage("InputFilter Line 70 source = " + source);
			Common.LogMessage("InputFilter Line 71 start = " + start);
			Common.LogMessage("InputFilter Line 72 end = " + end);
			Common.LogMessage("InputFilter Line 73 dest = " + dest);
			Common.LogMessage("InputFilter Line 74 dstart = " + dstart);
			Common.LogMessage("InputFilter Line 75 dend = " + dend);

			if(dstart >= 9 && dend >= 9)
				return "";

			String input = String.valueOf(dest.subSequence(0, dstart)) +
					source.subSequence(start, end) +
					dest.subSequence(dend, dest.length());
			Common.LogMessage("InputFilter Line 73 input = " + input);
			String output = filterString(input);
			Common.LogMessage("InputFilter Line 75 output = " + output);

			if(output.equals(input))
				return null;

			if(dstart == dend)
			{
				Common.LogMessage("InputFilter Line 82 new output.substring() = " + output.substring(dstart, dend + 1));
				return output.substring(dstart, dend + 1);
			}

			if(dstart < dend)
			{
				Common.LogMessage("InputFilter Line 88 new output.substring() = " + output.substring(dstart, dend));
				return output.substring(dstart, dend);
			}

			Common.LogMessage("InputFilter Line 92 new output = " + output);
			return output;
		}});

		setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

		addTextChangedListener(new TextWatcher()
		{
			private boolean isUpdating = false;

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
				Common.LogMessage("afterTextChanged Line 156 s = " + s);

				if(isUpdating)
					return;

				isUpdating = true;

				String str = s.toString();
				Common.LogMessage("afterTextChanged Line 164 str = " + str);

				str = str.replaceAll(String.valueOf(fixedChar), "");
				Common.LogMessage("afterTextChanged Line 167 str = " + str);
				str = fixedChar + str;

				Common.LogMessage("afterTextChanged Line 170 str = " + str);

				if(str.length() > 9)
					str = str.substring(0, 9);

				Common.LogMessage("afterTextChanged Line 175 str = " + str);

				s.replace(0, s.length(), str);

				Common.LogMessage("afterTextChanged Line 179 s = " + s);
				isUpdating = false;
			}
		});
	}

	@Override
	public void setText(CharSequence text, BufferType type)
	{
		String input = text.toString();
		input = filterString(input);
		Common.LogMessage("CustomEditText Line 189 input.length() = " + input.length());
		Common.LogMessage("CustomEditText Line 190 input padded = " + input);
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
		if(sel == 0)
			setSelection(1);
	}
}