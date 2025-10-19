This project has been forked as the original project is no longer maintained, not only has it been updated to SDK36 but now has custom widgets to put most of the logic in the library itself.

# ColourPicker EditText
A simple, minimalistic and beautiful dialog color picker for Android 4.1+ devices. This color picker is easy-to-use and easy-to-integrate in your application to let users of your app choose color in a simple way.

Features
- Get Hex and (A)RGB color codes
- Set color using (A)RGB values and get HEX codes
- Set color using HEX value
- Separate UI for portrait and landscape devices
- Support for pre-lollipop devices

Design inspired from [Dribbble](https://dribbble.com/shots/1858968-Material-Design-colorpicker) by Lucas Bonomi

Portrait

![Portrait RGB](screenshots/main_portrait_rgb.png)
![Portrait ARGB](screenshots/main_portrait_argb.png)

Landscape

![Landscape RGB](screenshots/main_landscape_rgb.png)
![Landscape ARGB](screenshots/main_landscape_argb.png)


## HOW TO USE IT

### Adding the library to your project
The aar library is available from the jitpack.io repository, to use it you must list the repository in your `settings.gradle`.
    
```text
pluginManagement {
	repositories {
		maven { url = "https://jitpack.io" }
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		maven { url = "https://jitpack.io" }
		google()
		mavenCentral()
	}
}

include ':app'
```
You may also need to remove other repository listings in build.gradle files as declaring in the settings.gradle file is now the best practice.

You also need to add an implementation line to the app build.gradle file.

```
implementation 'com.github.evilbunny2008:ColourPicker:2.0.12'
```

### How to add a widget to a layout

Open up a xml layout and add a CPEditText widget, note that because ColourPicker is now based on Material3 widgets you need to declare CPEditText inside a TextInputLayout widget:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="match_parent">
	<com.google.android.material.textfield.TextInputLayout
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:hint="@string/textHint">
		<com.github.evilbunny2008.colourpicker.CPEditText
			android:id="@+id/myCPEditText"
			android:layout_width="match_parent"
			android:layout_height="wrap_content" />
	</com.google.android.material.textfield.TextInputLayout>
</LinearLayout>
```
### Java code to handle a CPEditText

As CPEditText extends TextInputEditText everything that works for a TextInputEditText widget will also work for a CPEditText.

In your activity add code similar to the following:

```
package com.example.myapp;

import android.os.Bundle;

import com.github.evilbunny2008.colourpicker.CPEditText;
import com.odiousapps.weewxweather.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity
{
	private CPEditText myCPEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		myCPEditText = findViewById(R.id.myCPEditText);
		
		...
	}
}
```

And that's all that's it takes! :) 

## Example

Example app that use Android Material Color Picker Dialog to let users choose the color of the Qr Code:

* [weeWX Weather App](https://play.google.com/store/apps/details?id=com.odiousapps.weewxweather) ([Source Code](https://github.com/evilbunny2008/weewxweatherApp/))

## LICENSE

```
The MIT License (MIT)

Copyright (c) 2017 Simone Pessotto (http://www.simonepessotto.it)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
