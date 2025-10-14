package com.github.evilbunny2008.androidmaterialcolorpickerdialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

public class MaterialColorPickerEditText extends AppCompatEditText
{
	private int selectedColor = Color.WHITE;

	public MaterialColorPickerEditText(Context context)
	{
		super(context);
		init(context);
	}

	public MaterialColorPickerEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public MaterialColorPickerEditText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@SuppressWarnings("unused")
	private void init(Context context)
	{
		setFocusable(false);
		setClickable(true);
		setText(R.string.pick_a_color);
		setBackgroundColor(selectedColor);

		setOnClickListener(v ->
		{
			// Use your library's ColorPicker dialog
			//ColorPicker colorPicker = new ColorPicker((android.app.Activity)context);

			for (java.lang.reflect.Method m : ColorPicker.class.getMethods())
			{
				Log.d("CP_METHOD", m.getName());
			}
		});
	}

	@SuppressWarnings("unused")
	public int getSelectedColor()
	{
		return selectedColor;
	}

	@SuppressWarnings("unused")
	public void setSelectedColor(int color)
	{
		this.selectedColor = color;
		setBackgroundColor(color);
		setText(String.format("#%06X", (0xFFFFFF & color)));
	}
}