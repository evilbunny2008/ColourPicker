package com.github.evilbunny2008.colourpicker;

import android.content.Context;
import android.util.AttributeSet;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class CPEditText extends CustomEditText
{
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
		setOnClickListener(this::handleClick);
	}
}