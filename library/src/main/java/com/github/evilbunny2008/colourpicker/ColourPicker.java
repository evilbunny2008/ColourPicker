package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.slider.Slider;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * This is the only class of the project. It consists in a custom dialog that shows the GUI
 * used for choosing a colour using three sliders or an input field.
 *
 * @author Simone Pessotto
 */

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class ColourPicker extends Dialog implements CPSlider.OnChangeListener
{
	private final Activity activity;
	private @ColorInt int current_colour;

	private View colourView;
	private CPSlider alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
	private CustomEditText hexCode;
	private int alpha, red, green, blue;

	private Callback callback;

	private boolean autoclose;

	/**
	 * Creator of the class. It will initialize the class with black colour as default
	 *
	 * @param activity The reference to the activity where the colour picker is called
	 */
	public ColourPicker(Activity activity)
	{
		super(activity);

		this.activity = activity;

		if(activity instanceof Callback)
			callback = (Callback)activity;

		this.alpha = 255;
		this.red = 0;
		this.green = 0;
		this.blue = 0;

		this.autoclose = false;
	}

	/**
	 * Creator of the class. It will initialize the class with the argb colour passed as default
	 *
	 * @param activity The reference to the activity where the colour picker is called
	 * @param colour ARGB colour
	 */
	public ColourPicker(Activity activity, @ColorInt int colour)
	{
		this(activity);

		this.current_colour = colour;

		this.alpha = Color.alpha(colour);
		this.red = Color.red(colour);
		this.green = Color.green(colour);
		this.blue = Color.blue(colour);
	}

	/**
	 * Creator of the class. It will initialize the class with the argb colour passed as default
	 *
	 * @param activity The reference to the activity where the colour picker is called
	 * @param alpha    Alpha value (0 - 255)
	 * @param red      Red colour for RGB values (0 - 255)
	 * @param green    Green colour for RGB values (0 - 255)
	 * @param blue     Blue colour for RGB values (0 - 255)
	 * <p>
	 *                 If the value of the colours it's not in the right range (0 - 255) it will
	 *                 be place at 0.
	 * @since v1.1.0
	 */
	public ColourPicker(Activity activity,
	                   @IntRange(from = 0, to = 255) int alpha,
	                   @IntRange(from = 0, to = 255) int red,
	                   @IntRange(from = 0, to = 255) int green,
	                   @IntRange(from = 0, to = 255) int blue)
	{
		this(activity);

		this.alpha = FormatHelper.assertColourValueInRange(alpha);
		this.red = FormatHelper.assertColourValueInRange(red);
		this.green = FormatHelper.assertColourValueInRange(green);
		this.blue = FormatHelper.assertColourValueInRange(blue);
	}

	/**
	 * Enable auto-dismiss for the dialog
	 */
	public void enableAutoClose()
	{
		this.autoclose = true;
	}

	/**
	 * Disable auto-dismiss for the dialog
	 */
	public void disableAutoClose()
	{
		this.autoclose = false;
	}

	public void setCallback(Callback listener)
	{
		callback = listener;
	}

	/**
	 * Simple onCreate function. Here there is the init of the GUI.
	 *
	 * @param savedInstanceState As usual ...
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_colour_picker);

		colourView = findViewById(R.id.colourView);

		hexCode = findViewById(R.id.hexCode);

		alphaSeekBar = findViewById(R.id.alphaSeekBar);
		redSeekBar = findViewById(R.id.redSeekBar);
		greenSeekBar = findViewById(R.id.greenSeekBar);
		blueSeekBar = findViewById(R.id.blueSeekBar);

		alphaSeekBar.addOnChangeListener(this);
		redSeekBar.addOnChangeListener(this);
		greenSeekBar.addOnChangeListener(this);
		blueSeekBar.addOnChangeListener(this);

		hexCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});

		hexCode.setOnEditorActionListener((v, actionId, event) ->
		{
			if (actionId == EditorInfo.IME_ACTION_SEARCH ||
					actionId == EditorInfo.IME_ACTION_DONE ||
					event.getAction() == KeyEvent.ACTION_DOWN &&
							event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
			{
				float f = Float.parseFloat(v.getText().toString());
				int i = (int)f;
				updateColourView(i);
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(hexCode.getWindowToken(), 0);

				return true;
			}

			return false;
		});

		final Button okColour = findViewById(R.id.okColourButton);
		okColour.setOnClickListener(view -> sendColour());
	}

	@Override
	public void onValueChange(@NonNull Slider slider, float value, boolean from_user)
	{
		updateColourView((int)value);
	}

	private void initUi()
	{
		colourView.setBackgroundColor(getColour());

		alphaSeekBar.setValue(alpha);
		redSeekBar.setValue(red);
		greenSeekBar.setValue(green);
		blueSeekBar.setValue(blue);

		String hex = FormatHelper.formatColourValues(alpha, red, green, blue);
		hex = Common.to_ARGB_hex(hex);
		hexCode.setText(hex);

		Common.LogMessage("line203 Setting hexCode to " + hex);
	}

	private void sendColour()
	{
		if(callback != null)
		{
			String hex;
			Editable edit = hexCode.getText();
			if(edit == null || edit.length() <= 1)
				hex = "#FF000000";
			else
				hex = edit.toString();

			hex = Common.to_ARGB_hex(hex);
			final int colour = Color.parseColor(hex);
			callback.onColourChosen(colour);
		}

		if(autoclose)
			dismiss();
	}

	public void setColour(@ColorInt int colour)
	{
		alpha = Color.alpha(colour);
		red = Color.red(colour);
		green = Color.green(colour);
		blue = Color.blue(colour);
	}

	 // Method that synchronizes the colour between the bars, the view, and the HEX code text.
	 // @param input HEX Code of the colour.

	private void updateColourView(int colour)
	{
		try
		{
			alpha = Color.alpha(colour);
			red = Color.red(colour);
			green = Color.green(colour);
			blue = Color.blue(colour);

			colourView.setBackgroundColor(getColour());

			alphaSeekBar.setValue(alpha);
			redSeekBar.setValue(red);
			greenSeekBar.setValue(green);
			blueSeekBar.setValue(blue);
		} catch (Exception e) {
			hexCode.setError(e.toString());
		}
	}

	/**
	 * Getter for the ALPHA value of the ARGB selected colour
	 *
	 * @return ALPHA Value Integer (0 - 255)
	 * @since v1.1.0
	 */
	public int getAlpha()
	{
		return alpha;
	}

	/**
	 * Getter for the RED value of the RGB selected colour
	 *
	 * @return RED Value Integer (0 - 255)
	 */
	public int getRed()
	{
		return red;
	}

	/**
	 * Getter for the GREEN value of the RGB selected colour
	 *
	 * @return GREEN Value Integer (0 - 255)
	 */
	public int getGreen()
	{
		return green;
	}


	/**
	 * Getter for the BLUE value of the RGB selected colour
	 *
	 * @return BLUE Value Integer (0 - 255)
	 */
	public int getBlue()
	{
		return blue;
	}

	////
	// Erlend: Added setters.
	////

	public void setAlpha(int alpha)
	{
		this.alpha = alpha;
	}

	public void setRed(int red)
	{
		this.red = red;
	}

	public void setGreen(int green)
	{
		this.green = green;
	}

	public void setBlue(int blue)
	{
		this.blue = blue;
	}

	public void setAll(int alpha, int red, int green, int blue)
	{
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Getter for the colour as Android Color class value.
	 * <p>
	 * From Android Reference: The Color class defines methods for creating and converting colour
	 * ints.
	 * Colours are represented as packed ints, made up of 4 bytes: alpha, red, green, blue.
	 * The values are unpremultiplied, meaning any transparency is stored solely in the alpha
	 * component, and not in the colour components.
	 *
	 * @return Selected colour as Android Color class value.
	 */
	public int getColour()
	{
		return Color.argb(alpha, red, green, blue);
	}

	@Override
	public void show()
	{
		super.show();
		initUi();
	}
}