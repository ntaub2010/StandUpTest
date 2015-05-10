package com.naomitaub.standuptest;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button txt1 = (Button) findViewById(R.id.loginButton);
        Button txt2 = (Button) findViewById(R.id.createAccountButton);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Medium.ttf");
        txt1.setTypeface(font);
        txt2.setTypeface(font);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void launchLogin(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void launchCreateAccount(View view) {

        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void launchEnterInfo(View view, String email) {

        Intent intent = new Intent(this, EnterInfoActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void launchTests(View view, String email) {

        Intent intent = new Intent(this, TestsActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void launchShortTest(View view, String email) {

        Intent intent = new Intent(this, ShortTestActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void launchMediumTest(View view, String email) {

        Intent intent = new Intent(this, MediumTestActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void launchLongTest(View view, String email) {

        Intent intent = new Intent(this, LongTestActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void launchResults(View view, String email, String testType, String date, String time, int lowest, int highest, int range) {

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("testType", testType);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("lowest", lowest);
        intent.putExtra("highest", highest);
        intent.putExtra("range", range);
        startActivity(intent);
    }

    public void launchSymptoms(View view) {

        Intent intent = new Intent(this, SymptomsActivity.class);
        startActivity(intent);
    }

    public void launchPreviousTests(View view, String email) {

        Intent intent = new Intent(this, PreviousTestsActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

}


