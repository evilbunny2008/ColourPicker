package com.github.evilbunny2008.colourpicker;

import androidx.annotation.ColorInt;

/**
 * Created by Patrick Geselbracht on 2017-03-04
 *
 * @author Patrick Geselbracht
 */

@SuppressWarnings("unused")
public interface Callback
{
	/**
	 * Gets called whenever a user chooses a colour from the colour picker, i.e., presses the
	 * "Choose" button.
	 *
	 * @param colour Colour chosen
	 */

	void onColourChosen(@ColorInt int colour);
}
