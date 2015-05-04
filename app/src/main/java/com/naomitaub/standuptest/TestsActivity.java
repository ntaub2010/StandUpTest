package com.naomitaub.standuptest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Naomi on 3/8/2015.
 */
public class TestsActivity extends MainActivity {

    String email;
    Button shortTestButton, mediumTestButton, longTestButton, previousTestsButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        shortTestButton = (Button) findViewById(R.id.shortTestButton);
        mediumTestButton = (Button) findViewById(R.id.mediumTestButton);
        longTestButton = (Button) findViewById(R.id.longTestButton);
        previousTestsButton = (Button) findViewById(R.id.previousTestsButton);

        shortTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchShortTest(view, email);
            }
        });

        mediumTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchMediumTest(view, email);
            }
        });

        longTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchLongTest(view, email);
            }
        });

        previousTestsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchPreviousTests(view, email);
            }
        });

    }
}
