package com.naomitaub.standuptest;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;

import com.naomitaub.standuptest.R;

/**
 * Created by Naomi on 3/8/2015.
 */
public class TestsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonShortTest = (Button) findViewById(R.id.shortTestButton);
        Button buttonMediumTest = (Button) findViewById(R.id.mediumTestButton);
        Button buttonLongTest = (Button) findViewById(R.id.longTestButton);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Medium.ttf");
        buttonShortTest.setTypeface(font);
        buttonMediumTest.setTypeface(font);
        buttonLongTest.setTypeface(font);

    }
}
