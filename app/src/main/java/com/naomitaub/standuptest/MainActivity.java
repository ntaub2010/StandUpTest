package com.naomitaub.standuptest;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;


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
        setContentView(R.layout.activity_login);
    }

    EditText createAccountEmail, createAccountPassword;
    Button createAccountButton;
    String email, password;
    Context ctxAccount = this;

    public void launchCreateAccount(View view) {
        setContentView(R.layout.activity_create_account);


        createAccountEmail = (EditText) findViewById(R.id.createAccountEmail);
        createAccountPassword = (EditText) findViewById(R.id.createAccountPassword);
        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                email = createAccountEmail.getText().toString();
                password = createAccountPassword.getText().toString();

                DatabaseOperations DB = new DatabaseOperations(ctxAccount);
                DB.putAccountInfo(DB, email, password);
                Toast.makeText(getBaseContext(), "Account creation successful", Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }

    EditText enterInfoFirstName, enterInfoLastName, enterInfoBirthdate, enterInfoAge;
    RadioButton femaleButton, maleButton, otherButton;
    Button enterInfoSaveButton;
    String enterInfoGender, EIFName, EILName, EIBDate;
    int EIAge;
    Context ctxEnterInfo = this;

    public void launchEnterInfo(View view) {
        setContentView(R.layout.activity_enter_info);

        enterInfoFirstName = (EditText) findViewById(R.id.firstNameText);
        enterInfoLastName = (EditText) findViewById(R.id.lastNameText);
        enterInfoBirthdate = (EditText) findViewById(R.id.birthdateText);
        enterInfoAge = (EditText) findViewById(R.id.ageText);

        enterInfoSaveButton = (Button)findViewById(R.id.saveInfoButton);
        enterInfoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EIFName = enterInfoFirstName.getText().toString();
                EILName = enterInfoLastName.getText().toString();
                EIBDate = enterInfoBirthdate.getText().toString();
                EIAge = Integer.parseInt(enterInfoAge.getText().toString());


                femaleButton = (RadioButton) findViewById(R.id.genderFemaleButton);
                maleButton = (RadioButton) findViewById(R.id.genderMaleButton);
                otherButton = (RadioButton) findViewById(R.id.genderOtherButton);

                if (femaleButton.isChecked())
                    enterInfoGender = "Female";

                else if (maleButton.isChecked())
                    enterInfoGender = "Male";

                else if (otherButton.isChecked())
                    enterInfoGender = "Other";

                DatabaseOperations DB = new DatabaseOperations(ctxEnterInfo);
                DB.putUserInfo(DB, EIFName, EILName, EIBDate, EIAge, enterInfoGender);

                Toast.makeText(getBaseContext(), "User info updating successful", Toast.LENGTH_LONG).show();
                //finish();

            }
        });

    }

    public void launchTests(View view) {
        setContentView(R.layout.activity_tests);
    }


    public Button buttonStart, buttonStop;
    public TextView textViewTimes;

    public void launchShortTest(View view) {
        setContentView(R.layout.activity_short_test);

        }

    Button buttonStartM, buttonPauseM, medEnterHR;
    public EditText medHR;
    private TextView timerValueM;

    private long startTimeM = 0L;

    private Handler customHandlerM = new Handler();

    public String timeM;
    long timeInMillisecondsM = 0L;
    long timeSwapBuffM = 0L;
    long updatedTimeM = 0L;
    public int LDHRM, countM = 0;
    private int medHRArray [] = new int[30], medHRArrayTime [] = new int[30];

    public void launchMediumTest(View view) {
        setContentView(R.layout.activity_medium_test);


        buttonStartM = (Button) findViewById(R.id.startTimerButtonMedium);
        buttonPauseM = (Button) findViewById(R.id.stopTimerButtonMedium);
        timerValueM = (TextView) findViewById(R.id.textViewTimeMedium);

        medHR = (EditText) findViewById(R.id.medHR);
        medEnterHR = (Button) findViewById(R.id.medEnterHRButton);


        buttonStartM.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTimeM = SystemClock.uptimeMillis();
                customHandlerM.postDelayed(updateTimerThreadM, 0);

                timeM = Integer.toString(calM.get(Calendar.HOUR_OF_DAY)) + ":" +
                        Integer.toString(calM.get(Calendar.MINUTE));
            }
        });

        buttonPauseM.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                timeSwapBuffM += timeInMillisecondsM;
                customHandlerM.removeCallbacks(updateTimerThreadM);

            }
        });

        //manage entered heart rate
        medEnterHR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                LDHRM = Integer.parseInt(medHR.getText().toString());
                medHRArray[countM] = LDHRM;
                int temp = (minsM * 60) + secsM;
                medHRArrayTime[countM] =  temp;
                countM++;
                medHR.setText("");
                Toast.makeText(getApplicationContext(), "HR saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    public TextView medMessage, medMessageHR;
    public int minsM, secsM, lowest, rangeM;
    public TextView resultsRange, resultsHigh, resultsLow;
    private Runnable updateTimerThreadM = new Runnable() {

        public void run() {

            medMessage = (TextView) findViewById(R.id.medTestMessage);
            medMessageHR = (TextView) findViewById(R.id.medTestMessageHR);

            timeInMillisecondsM = SystemClock.uptimeMillis() - startTimeM;

            updatedTimeM = timeSwapBuffM + timeInMillisecondsM;

            secsM = (int) (updatedTimeM / 1000);
            minsM = secsM / 60;
            secsM = secsM % 60;
            timerValueM.setText(String.format("%02d", minsM) + ":"
                    + String.format("%02d", secsM));
            customHandlerM.postDelayed(this, 0);

            //update heart rate message
            if (    (secsM == 35 && minsM == 0) ||
                    (secsM == 20 && minsM == 1) ||
                    (secsM == 20 && minsM == 2) ||
                    (secsM == 05 && minsM == 3) ||
                    (secsM == 50 && minsM == 3) ||
                    (secsM == 05 && minsM == 5) ||
                    (secsM == 50 && minsM == 5)) {
                //playNotificationSound();
                medMessageHR.setText("Prepare to enter heart rate");
            }
            if (    (secsM == 45 && minsM == 0) ||
                    (secsM == 30 && minsM == 1) ||
                    (secsM == 30 && minsM == 2) ||
                    (secsM == 15 && minsM == 3) ||
                    (secsM == 00 && minsM == 4) ||
                    (secsM == 15 && minsM == 5) ||
                    (secsM == 00 && minsM == 6)) {
                medMessageHR.setText("Enter heart rate");
            }
            if (    (secsM == 55 && minsM == 0) ||
                    (secsM == 40 && minsM == 1) ||
                    (secsM == 40 && minsM == 2) ||
                    (secsM == 25 && minsM == 3) ||
                    (secsM == 10 && minsM == 4) ||
                    (secsM == 25 && minsM == 5) ||
                    (secsM == 10 && minsM == 6)) {
                medMessageHR.setText("");
            }

            //update position message
            if (minsM == 2 && secsM == 0) {
                //playChangeSound();
                medMessage.setText("You should be STANDING");
            }
            if (minsM == 4 && secsM == 0) {
                //playChangeSound();
                medMessage.setText("You should be LYING DOWN");
            }
            if (minsM == 6 && secsM == 0) {
                //playChangeSound();
                medMessage.setText("Test completed!");
            }

        }

    };

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


    Button buttonStartl, buttonStopl;
    public static TextView resultsDate, resultsTime, resultsBlurb;
    public String dateM;
    public Calendar calM = new GregorianCalendar();
    public void launchLongTest(View view) {
        setContentView(R.layout.activity_long_test);
    }

    public void launchResults(View view) {
        setContentView(R.layout.activity_results);
        //get array length - it's actually just count
        int temp1 = medHRArray[0];
        int temp2 = medHRArray[0];
        int temp3 = medHRArray[0];
        for (int i = 1; i < medHRArray.length; i++) {
            if (medHRArrayTime[i] <= 120) {
                //loop through to find lowest val
                if ((medHRArray[i] < medHRArray[i - 1]) && medHRArray[i] != 0) {
                    temp1 = medHRArray[i];
                }
                //find range of BPM
            }

            if (medHRArrayTime[i] < 240) {
                if (medHRArray[i] > medHRArray[i - 1]) {
                    temp2 = medHRArray[i];
                }

            }

            if (medHRArrayTime[i] >= 240) {
                if ((medHRArray[i] < medHRArray[i - 1]) && medHRArray[i] != 0) {
                    temp3 = medHRArray[i];
                }
            }
        }

        if (temp1 < temp3) {
            lowest = temp1;
        }
        else if (temp3 < temp1) {
            lowest = temp3;
        }
        //go through array
        //based on time, calculate BPM
        rangeM = temp2 - lowest;
        resultsRange = (TextView) findViewById(R.id.resultsRangeVal);
        resultsHigh = (TextView) findViewById(R.id.resultsHighVal);
        resultsLow = (TextView) findViewById(R.id.resultsLowVal);
        resultsDate = (TextView) findViewById(R.id.resultsDate);
        resultsTime = (TextView) findViewById(R.id.resultsTime);
        resultsBlurb = (TextView) findViewById(R.id.resultsBlurb);

        dateM = Integer.toString((calM.get(Calendar.MONTH)) + 1) + "/" +
                Integer.toString(calM.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(calM.get(Calendar.YEAR));

        resultsDate.setText("Date: " + dateM);
        resultsTime.setText("Time Started: " + timeM);
        resultsRange.setText(Integer.toString(rangeM));
        resultsHigh.setText(Integer.toString(temp2));
        resultsLow.setText(Integer.toString(lowest));

        if (rangeM < 20) {
            resultsBlurb.setText("Based on these test results, you most likely do NOT have POTS.");
        }

        if (rangeM >= 20 && rangeM < 30) {
            resultsBlurb.setText("Based on these test results, you may have POTS.");
        }

        if (rangeM >= 30) {
            resultsBlurb.setText("Based on these test results, there is a high chance that you may have POTS.");
        }
    }

    public TextView symptomsBL;
    public TextView symptomsLinkDI, symptomsLinkDINET;

    public void launchSymptoms(View view) {
        setContentView(R.layout.activity_symptoms);

        symptomsBL = (TextView) findViewById(R.id.symptomsBL);
        symptomsBL.setText(Html.fromHtml(getString(R.string.symptoms)));



        symptomsLinkDI = (TextView) findViewById(R.id.dysautonomiaInternationalLinkTextView);
        symptomsLinkDINET = (TextView) findViewById(R.id.dinetSymptomsLinkTextView);



        //stripUnderlines(symptomsLinkDI);
        //stripUnderlines(Text2);

        /*symptomsLinkDI.setText(Html.fromHtml(
                "<b>text3:</b> Text with a " +
                "<a href=\"http://google.com\">link</a> "));
        symptomsLinkDI.setMovementMethod(LinkMovementMethod.getInstance());*/

        //symptomsLinkDINET.setText(Html.fromHtml("<a href=\"http://www.dinet.org/index.php/information-resources/pots-place/pots-symptoms\">POTS Overview from Dysatuonomia International</a>"));
        //symptomsLinkDINET.setMovementMethod(LinkMovementMethod.getInstance());

       // symptomsLinkDI.setText(Html.fromHtml("<a href=\"http://www.dysautonomiainternational.org/page.php?ID=30\">POTS Symptoms from DINET</a>"));
        //symptomsLinkDI.setMovementMethod(LinkMovementMethod.getInstance());

        /*private void stripUnderlines(TextView textView) {
            Spannable s = (Spannable)textView.getText();
            URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
            for (URLSpan span: spans) {
                int start = s.getSpanStart(span);
                int end = s.getSpanEnd(span);
                s.removeSpan(span);
                span = new URLSpanNoUnderline(span.getURL());
                s.setSpan(span, start, end, 0);
            }
            textView.setText(s);
        }

        class URLSpanNoUnderline extends URLSpan {
            public URLSpanNoUnderline(String url) {
                super(url);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }*/
    }

    public void launchPreviousTests(View view) {
        setContentView(R.layout.activity_previous_tests);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}


