package com.github.evilbunny2008.ColourPicker;

import androidx.annotation.IntRange;

@SuppressWarnings("unused")
class FormatHelper
{
	/**
	 * Checks whether the specified value is between (including bounds) 0 and 255
	 *
	 * @param colourValue Colour value
	 * @return Specified input value if between 0 and 255, otherwise 0
	 */
	static int assertColourValueInRange(@IntRange(from = 0, to = 255) int colourValue)
	{
		return ((0 <= colourValue) && (colourValue <= 255)) ? colourValue : 0;
	}

	/**
	 * Formats individual ARGB values to be output as an 8 character HEX string.
	 * <p>
	 * Beware: If any value is lower than 0 or higher than 255, it's reset to 0.
	 *
	 * @param alpha Alpha value
	 * @param red   Red colour value
	 * @param green Green colour value
	 * @param blue  Blue colour value
	 * @return HEX String containing the three values
	 * @since v1.1.0
	 */
	static String formatColourValues(
			@IntRange(from = 0, to = 255) int alpha,
			@IntRange(from = 0, to = 255) int red,
			@IntRange(from = 0, to = 255) int green,
			@IntRange(from = 0, to = 255) int blue)
	{
		return String.format("%02X%02X%02X%02X",
				assertColourValueInRange(alpha),
				assertColourValueInRange(red),
				assertColourValueInRange(green),
				assertColourValueInRange(blue)
		);
	}
}
