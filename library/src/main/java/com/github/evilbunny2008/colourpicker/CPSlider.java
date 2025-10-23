package com.github.evilbunny2008.colourpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.slider.Slider;

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class CPSlider extends Slider
{
	public CPSlider(Context context)
	{
		super(context);
		init(null);
	}

	public CPSlider(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(attrs);
	}

	public CPSlider(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(AttributeSet attrs)
	{
		// TODO
	}
}