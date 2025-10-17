package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

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
	}
}