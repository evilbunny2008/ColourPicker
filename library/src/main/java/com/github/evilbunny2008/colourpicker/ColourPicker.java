package com.github.evilbunny2008.colourpicker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * This is the only class of the project. It consists in a custom dialog that shows the GUI
 * used for choosing a colour using three sliders or an input field.
 *
 * @author Simone Pessotto
 */

@SuppressWarnings({"unused","FieldMayBeFinal", "FieldCanBeLocal"})
public class ColourPicker extends Dialog implements CPSlider.OnChangeListener, CustomEditText.OnEditorActionListener
{
	private final Activity activity;
	private OnTextChangedListener listener;

	private View colourView;
	private CPSlider alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
	private CustomEditText hexCode;
	private int alpha = 255;
	private int red = 0;
	private int green = 0;
	private int blue = 0;

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

		this.alpha = Color.alpha(colour);
		this.red = Color.red(colour);
		this.green = Color.green(colour);
		this.blue = Color.blue(colour);
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

		hexCode.setOnEditorActionListener(this);

		final Button okColour = findViewById(R.id.okColourButton);
		okColour.setOnClickListener(view -> sendColour());
	}

	@Override
	public void show()
	{
		super.show();

		int colour = getColour();
		String hex = Common.to_ARGB_hex(colour);

		colourView.setBackgroundColor(colour);

		alphaSeekBar.setValue(alpha);
		redSeekBar.setValue(red);
		greenSeekBar.setValue(green);
		blueSeekBar.setValue(blue);

		hexCode.setText(hex);

		Common.LogMessage("ColourPicker line 146 Setting hexCode to " + hex);
	}

	private void sendColour()
	{
		Editable edit = hexCode.getText();
		if(edit != null)
		{
			String hex = hexCode.filterString(edit.toString());
			Common.LogMessage("ColourPicker line 183 Setting hexCode to " + hex);
			if(callback != null)
				callback.onColourChosen(hex);

			if(autoclose)
				dismiss();
		}
	}

	public void setColour(@ColorInt int colour)
	{
		alpha = Color.alpha(colour);
		red = Color.red(colour);
		green = Color.green(colour);
		blue = Color.blue(colour);
	}

	@Override
	public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
	{
		Common.LogMessage("setOnEditorActionListener()");

		CharSequence tmp = textView.getText();
		if(tmp == null)
			return false;

		String str = tmp.toString();
		if(str.isEmpty())
			return false;

		str = hexCode.filterString(str);

		Common.LogMessage("hexCode changed... hexCode = " + str);
		updateText(str);
		sendColour();
		//dismiss();

		return true;
/*
		if (actionId == EditorInfo.IME_ACTION_SEARCH ||
				actionId == EditorInfo.IME_ACTION_DONE ||
				event.getAction() == KeyEvent.ACTION_DOWN &&
						event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
		{


			onTextChanged(tmp.toString());
			Common.LogMessage("hexCode changed... hexCode = " + tmp);
			InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(hexCode.getWindowToken(), 0);

			return true;
		}
		return false;
*/
	}

	private void updateText(String text)
	{
		if(listener != null)
			listener.onTextChanged(text);
	}

	@Override
	public void onValueChange(@NonNull Slider slider, float value, boolean from_user)
	{
		if(from_user)
		{
			alpha = (int)alphaSeekBar.getValue();
			red = (int)redSeekBar.getValue();
			green = (int)greenSeekBar.getValue();
			blue = (int)blueSeekBar.getValue();

			String hex = Common.to_ARGB_hex(getColour());
			Common.LogMessage("ColourPicker line 194 Setting hexCode to " + hex);
			hexCode.post(() -> hexCode.setText(hex));
			colourView.post(() -> colourView.setBackgroundColor(getColour()));
		}
	}

	public interface OnTextChangedListener
	{
		void onTextChanged(String colour);
	}

	public void setTextCallback(OnTextChangedListener listener)
	{
		this.listener = listener;
	}

	void onTextChanged(CharSequence text, int start, int before, int count)
	{
		if (listener != null)
			listener.onTextChanged(text.toString());
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

	public void setAll(@ColorInt int colour)
	{
		setAll(Color.alpha(colour), Color.red(colour), Color.green(colour), Color.blue(colour));
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
}