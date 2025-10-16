package com.github.evilbunny2008.ColourPicker;

import android.content.Context;
import android.util.AttributeSet;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
class Slider extends com.google.android.material.slider.Slider
{
	public Slider(Context context)
	{
		super(context);
		init(null);
	}

	public Slider(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(attrs);
	}

	public Slider(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(AttributeSet attrs)
	{
		// TODO
	}
}
