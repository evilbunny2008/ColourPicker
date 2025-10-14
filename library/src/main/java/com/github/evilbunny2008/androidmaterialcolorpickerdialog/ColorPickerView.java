package com.github.evilbunny2008.androidmaterialcolorpickerdialog;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

@SuppressWarnings("")
public class ColorPickerView extends AppCompatEditText
{
	//private int selectedColor = Color.WHITE;

	public ColorPickerView(Context context)
	{
		super(context);
		init(context);
	}

	public ColorPickerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr)
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
		//setBackgroundColor(selectedColor);

		setOnClickListener(v ->
		{
			ColorPicker picker = new ColorPicker((android.app.Activity) context);
			picker.setCancelable(true);
			picker.show();

			// If the ColorPicker supports getColor(), update the text field after closing
			picker.setOnDismissListener(dialog ->
			{
				int color = picker.getColor();
				setText(String.format("#%06X", (0xFFFFFF & color)));
				setBackgroundColor(color);
			});
		});
	}
}