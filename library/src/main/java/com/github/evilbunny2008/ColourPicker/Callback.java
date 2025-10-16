package com.github.evilbunny2008.ColourPicker;

import androidx.annotation.ColorInt;

/**
 * Created by Patrick Geselbracht on 2017-03-04
 *
 * @author Patrick Geselbracht
 */
public interface Callback
{
	/**
	 * Gets called whenever a user chooses a colour from the ColourPicker, i.e., presses the
	 * "Choose" button.
	 *
	 * @param colour Colour chosen
	 */

	void onColourChosen(@ColorInt int colour);
}
