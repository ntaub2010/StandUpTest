package com.naomitaub.standuptest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

    public void launchEnterInfo(View view) {

        Intent intent = new Intent(this, EnterInfoActivity.class);
        startActivity(intent);
    }

    public void launchTests(View view) {

        Intent intent = new Intent(this, TestsActivity.class);
        startActivity(intent);
    }

    public void launchShortTest(View view) {

        Intent intent = new Intent(this, ShortTestActivity.class);
        startActivity(intent);
    }

    public void launchMediumTest(View view) {

        Intent intent = new Intent(this, MediumTestActivity.class);
        startActivity(intent);
    }

    public void playNotificationSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   // final MediaPlayer changeSound = MediaPlayer.create(this, R.raw.change_sound);
    //taken from http://oringz.com/ringtone/solemn/

   // public void playChangeSound() {
   //     changeSound.start();
    //}

    public void launchLongTest(View view) {

        Intent intent = new Intent(this, LongTestActivity.class);
        startActivity(intent);
    }

    public void launchResults(View view, String testType, String date, String time, int lowest, int highest, int range) {

        Intent intent = new Intent(this, ResultsActivity.class);
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

    public void launchPreviousTests(View view) {

        Intent intent = new Intent(this, PreviousTestsActivity.class);
        startActivity(intent);
    }

}


