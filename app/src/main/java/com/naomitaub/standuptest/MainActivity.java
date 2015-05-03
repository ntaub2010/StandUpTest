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
import android.database.Cursor;
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
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    EditText loginEmail, loginPassword;
    String login_email, login_password;
    Context ctxLogin = this;
    Button signInButton;
    boolean loginStatus;
    Cursor CR;

    public void launchLogin(View view) {
        setContentView(R.layout.activity_login);

        signInButton = (Button) findViewById(R.id.email_sign_in_button);

        loginEmail = (EditText)findViewById(R.id.email);
        loginPassword = (EditText)findViewById(R.id.password);


        //String username = "";

        signInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login_email = loginEmail.getText().toString();
                login_password = loginPassword.getText().toString();

                DatabaseOperations DOP = new DatabaseOperations(ctxLogin);
                CR = DOP.getLoginInfo(DOP);
                //CR.moveToFirst();
                loginStatus = false;

                if( CR != null && CR.moveToFirst() ){
                    //num = cursor.getString(cursor.getColumnIndex("ContactNumber"));
                    do {

                        if(login_email.equals(CR.getString(0)) && login_password.equals(CR.getString(1))) {
                            loginStatus = true;
                            //username = CR.getString(0);
                        }
                    } while (CR.moveToNext());
                    CR.close();
                }

                if(loginStatus) {
                    Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();
                    launchTests(v);
                    //finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error: Login failed", Toast.LENGTH_LONG).show();
                    //finish();
                }
            }
        });

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
                launchEnterInfo(v);
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
                launchTests(v);
                //finish();

            }
        });

    }

    public void launchTests(View view) {
        setContentView(R.layout.activity_tests);
    }


    //Beginning of Short Test code

    Button buttonStartS, buttonPauseS, shortEnterHR;
    public EditText shortHR;
    private TextView timerValueS;

    private long startTimeS = 0L;

    private Handler customHandlerS = new Handler();

    public String time, date, testType;
    long timeInMillisecondsS = 0L, timeSwapBuffS = 0L, updatedTimeS = 0L;
    public int LDHRS, countS = 0, minsS, secsS, lowest, range, temp1, temp2, temp3;
    private int shortHRArray [] = new int[10], shortHRArrayTime [] = new int[10];

    public TextView shortMessage, shortMessageHR;
    public Calendar cal = new GregorianCalendar();

    public void launchShortTest(View view) {
        setContentView(R.layout.activity_short_test);

        testType = "Short";

        buttonStartS = (Button) findViewById(R.id.startTimerButtonShort);
        buttonPauseS = (Button) findViewById(R.id.stopTimerButtonShort);
        timerValueS = (TextView) findViewById(R.id.textViewTimeShort);

        shortHR = (EditText) findViewById(R.id.shortHR);
        shortEnterHR = (Button) findViewById(R.id.shortEnterHRButton);


        buttonStartS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTimeS = SystemClock.uptimeMillis();
                customHandlerS.postDelayed(updateTimerThreadS, 0);

                time = Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        Integer.toString(cal.get(Calendar.MINUTE));
            }
        });

        buttonPauseS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                timeSwapBuffS += timeInMillisecondsS;
                customHandlerS.removeCallbacks(updateTimerThreadS);

            }
        });

        //manage entered heart rate
        shortEnterHR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                LDHRS = Integer.parseInt(shortHR.getText().toString());
                shortHRArray[countS] = LDHRS;
                int temp = (minsS * 60) + secsS;
                shortHRArrayTime[countS] = temp;
                countS++;
                shortHR.setText("");
                Toast.makeText(getApplicationContext(), "HR saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Runnable updateTimerThreadS = new Runnable() {

        public void run() {

            shortMessage = (TextView) findViewById(R.id.shortTestMessage);
            shortMessageHR = (TextView) findViewById(R.id.shortTestMessageHR);

            timeInMillisecondsS = SystemClock.uptimeMillis() - startTimeS;

            updatedTimeS = timeSwapBuffS + timeInMillisecondsS;

            secsS = (int) (updatedTimeS / 1000);
            minsS = secsS / 60;
            secsS = secsS % 60;
            timerValueS.setText(String.format("%02d", minsS) + ":"
                    + String.format("%02d", secsS));
            customHandlerS.postDelayed(this, 0);

            //update heart rate message
            if (    (secsS == 05 && minsS == 0) ||
                    (secsS == 35 && minsS == 0) ||
                    (secsS == 05 && minsS == 1) ||
                    (secsS == 35 && minsS == 1) ||
                    (secsS == 05 && minsS == 2) ||
                    (secsS == 35 && minsS == 2)) {
                //playNotificationSound();
                shortMessageHR.setText("Prepare to enter heart rate");
            }
            if (    (secsS == 15 && minsS == 0) ||
                    (secsS == 45 && minsS == 0) ||
                    (secsS == 15 && minsS == 1) ||
                    (secsS == 45 && minsS == 1) ||
                    (secsS == 15 && minsS == 2) ||
                    (secsS == 45 && minsS == 2)) {
                shortMessageHR.setText("Enter heart rate");
            }
            if (    (secsS == 25 && minsS == 0) ||
                    (secsS == 55 && minsS == 0) ||
                    (secsS == 25 && minsS == 1) ||
                    (secsS == 55 && minsS == 1) ||
                    (secsS == 25 && minsS == 2) ||
                    (secsS == 55 && minsS == 2)) {
                shortMessageHR.setText("");
            }

            //update position message
            if (minsS == 1 && secsS == 0) {
                //playChangeSound();
                shortMessage.setText("You should be STANDING");
            }
            if (minsS == 2 && secsS == 0) {
                //playChangeSound();
                shortMessage.setText("You should be SITTING DOWN");
            }
            if (minsS == 3 && secsS == 0) {
                //playChangeSound();
                shortMessage.setText("Test completed!");
            }

            temp1 = shortHRArray[0];
            temp2 = shortHRArray[0];
            temp3 = shortHRArray[0];
            for (int i = 1; i < shortHRArray.length; i++) {
                if (shortHRArrayTime[i] <= 60) {
                    //loop through to find lowest val
                    if ((shortHRArray[i] < shortHRArray[i - 1]) && shortHRArray[i] != 0) {
                        temp1 = shortHRArray[i];
                    }
                    //find range of BPM
                }

                if (shortHRArrayTime[i] < 120) {
                    if (shortHRArray[i] > shortHRArray[i - 1]) {
                        temp2 = shortHRArray[i];
                    }

                }

                if (shortHRArrayTime[i] >= 120) {
                    if ((shortHRArray[i] < shortHRArray[i - 1]) && shortHRArray[i] != 0) {
                        temp3 = shortHRArray[i];
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
            range = temp2 - lowest;

        }



    };

    //Beginning of Medium Test code

    Button buttonStartM, buttonPauseM, medEnterHR;
    public EditText medHR;
    private TextView timerValueM;

    private long startTimeM = 0L;

    private Handler customHandlerM = new Handler();
    long timeInMillisecondsM = 0L;
    long timeSwapBuffM = 0L;
    long updatedTimeM = 0L;
    public int LDHRM, countM = 0;
    private int medHRArray [] = new int[30], medHRArrayTime [] = new int[30];

    public TextView medMessage, medMessageHR;
    public int minsM, secsM;

    public void launchMediumTest(View view) {
        setContentView(R.layout.activity_medium_test);

        testType = "Medium";

        buttonStartM = (Button) findViewById(R.id.startTimerButtonMedium);
        buttonPauseM = (Button) findViewById(R.id.stopTimerButtonMedium);
        timerValueM = (TextView) findViewById(R.id.textViewTimeMedium);

        medHR = (EditText) findViewById(R.id.medHR);
        medEnterHR = (Button) findViewById(R.id.medEnterHRButton);


        buttonStartM.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTimeM = SystemClock.uptimeMillis();
                customHandlerM.postDelayed(updateTimerThreadM, 0);

                time = Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        Integer.toString(cal.get(Calendar.MINUTE));
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
                    (secsM == 40 && minsM == 3) ||
                    (secsM == 05 && minsM == 5) ||
                    (secsM == 40 && minsM == 5)) {
                //playNotificationSound();
                medMessageHR.setText("Prepare to enter heart rate");
            }
            if (    (secsM == 45 && minsM == 0) ||
                    (secsM == 30 && minsM == 1) ||
                    (secsM == 30 && minsM == 2) ||
                    (secsM == 15 && minsM == 3) ||
                    (secsM == 50 && minsM == 3) ||
                    (secsM == 15 && minsM == 5) ||
                    (secsM == 50 && minsM == 5)) {
                medMessageHR.setText("Enter heart rate");
            }
            if (    (secsM == 55 && minsM == 0) ||
                    (secsM == 40 && minsM == 1) ||
                    (secsM == 40 && minsM == 2) ||
                    (secsM == 25 && minsM == 3) ||
                    (secsM == 10 && minsM == 4) ||
                    (secsM == 25 && minsM == 5)) {
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

            temp1 = medHRArray[0];
            temp2 = medHRArray[0];
            temp3 = medHRArray[0];
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
            range = temp2 - lowest;

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



    //Beginning of Long Test code

    Button buttonStartL, buttonPauseL, longEnterHR;
    public EditText longHR;
    private TextView timerValueL;

    private long startTimeL = 0L;

    private Handler customHandlerL = new Handler();
    long timeInMillisecondsL = 0L;
    long timeSwapBuffL = 0L;
    long updatedTimeL = 0L;
    public int LDHRL, countL = 0;
    private int longHRArray [] = new int[60], longHRArrayTime [] = new int[60];

    public TextView longMessage, longMessageHR;
    public int minsL, secsL;

    public void launchLongTest(View view) {
        setContentView(R.layout.activity_long_test);

        testType = "Long";

        buttonStartL = (Button) findViewById(R.id.startTimerButtonLong);
        buttonPauseL = (Button) findViewById(R.id.stopTimerButtonLong);
        timerValueL = (TextView) findViewById(R.id.textViewTimeLong);

        longHR = (EditText) findViewById(R.id.longHR);
        longEnterHR = (Button) findViewById(R.id.longEnterHRButton);


        buttonStartL.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTimeL = SystemClock.uptimeMillis();
                customHandlerL.postDelayed(updateTimerThreadL, 0);

                time = Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        Integer.toString(cal.get(Calendar.MINUTE));
            }
        });

        buttonPauseL.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                timeSwapBuffL += timeInMillisecondsL;
                customHandlerL.removeCallbacks(updateTimerThreadL);

            }
        });

        //manage entered heart rate
        longEnterHR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                LDHRL = Integer.parseInt(longHR.getText().toString());
                longHRArray[countL] = LDHRL;
                int temp = (minsL * 60) + secsL;
                longHRArrayTime[countL] = temp;
                countL++;
                longHR.setText("");
                Toast.makeText(getApplicationContext(), "HR saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Runnable updateTimerThreadL = new Runnable() {

        public void run() {

            longMessage = (TextView) findViewById(R.id.longTestMessage);
            longMessageHR = (TextView) findViewById(R.id.longTestMessageHR);

            timeInMillisecondsL = SystemClock.uptimeMillis() - startTimeL;

            updatedTimeL = timeSwapBuffL + timeInMillisecondsL;

            secsL = (int) (updatedTimeL / 1000);
            minsL = secsL / 60;
            secsL = secsL % 60;
            timerValueL.setText(String.format("%02d", minsL) + ":"
                    + String.format("%02d", secsL));
            customHandlerL.postDelayed(this, 0);

            //update heart rate message
            if (    (secsL == 40 && minsL == 1) ||
                    (secsL == 40 && minsL == 4) ||
                    (secsL == 40 && minsL == 9)) {
                //playNotificationSound();
                longMessageHR.setText("Prepare to enter heart rate");
            }
            if (    (secsL == 50 && minsL == 1) ||
                    (secsL == 50 && minsL == 4) ||
                    (secsL == 50 && minsL == 9)) {
                longMessageHR.setText("Enter heart rate");
            }
            if (    (secsL == 00 && minsL == 2) ||
                    (secsL == 00 && minsL == 5) ||
                    (secsL == 00 && minsL == 10)) {
                longMessageHR.setText("");
            }

            //update position message
            if (minsL == 10 && secsL == 0) {
                //playChangeSound();
                longMessage.setText("You should be STANDING");
            }
            if (minsL == 20 && secsL == 0) {
                //playChangeSound();
                longMessage.setText("You should be LYING DOWN");
            }
            if (minsL == 30 && secsL == 0) {
                //playChangeSound();
                longMessage.setText("Test completed!");
            }

            temp1 = longHRArray[0];
            temp2 = longHRArray[0];
            temp3 = longHRArray[0];
            for (int i = 1; i < longHRArray.length; i++) {
                if (longHRArrayTime[i] <= 600) {
                    //loop through to find lowest val
                    if ((longHRArray[i] < longHRArray[i - 1]) && longHRArray[i] != 0) {
                        temp1 = longHRArray[i];
                    }
                    //find range of BPM
                }

                if (longHRArrayTime[i] < 1200) {
                    if (longHRArray[i] > longHRArray[i - 1]) {
                        temp2 = longHRArray[i];
                    }

                }

                if (longHRArrayTime[i] >= 1200) {
                    if ((longHRArray[i] < longHRArray[i - 1]) && longHRArray[i] != 0) {
                        temp3 = longHRArray[i];
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
            range = temp2 - lowest;

        }

    };

    public static TextView resultsDate, resultsTime, resultsBlurb, resultsRange, resultsHigh, resultsLow;
    String dateString, timeString, blurbString;
    int rangeInt, highInt, lowInt;
    Context ctxResults = this;

    public void launchResults(View view) {
        setContentView(R.layout.activity_results);
        //get array length - it's actually just count

        resultsRange = (TextView) findViewById(R.id.resultsRangeVal);
        resultsHigh = (TextView) findViewById(R.id.resultsHighVal);
        resultsLow = (TextView) findViewById(R.id.resultsLowVal);
        resultsDate = (TextView) findViewById(R.id.resultsDate);
        resultsTime = (TextView) findViewById(R.id.resultsTime);
        resultsBlurb = (TextView) findViewById(R.id.resultsBlurb);

        date = Integer.toString((cal.get(Calendar.MONTH)) + 1) + "/" +
                Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(cal.get(Calendar.YEAR));

        resultsDate.setText("Date: " + date);
        resultsTime.setText("Time Started: " + time);
        resultsRange.setText(Integer.toString(range));
        resultsHigh.setText(Integer.toString(temp2));
        resultsLow.setText(Integer.toString(lowest));

        if (range < 20) {
            resultsBlurb.setText("Based on these test results, you most likely do NOT have POTS.");
        }

        if (range >= 20 && range < 30) {
            resultsBlurb.setText("Based on these test results, you may have POTS.");
        }

        if (range >= 30) {
            resultsBlurb.setText("Based on these test results, there is a high chance that you may have POTS.");
        }

        dateString = date;
        timeString = time;
        lowInt = lowest;
        highInt = temp2;
        rangeInt = range;
        blurbString = (resultsBlurb.getText()).toString();
        //testType = "test";

        DatabaseOperations DB = new DatabaseOperations(ctxResults);
        DB.putRecordInfo(DB, testType, dateString, timeString, lowInt, highInt, rangeInt, blurbString);

        Toast.makeText(getBaseContext(), "Record insertion successful", Toast.LENGTH_LONG).show();
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

    ListView allRecords;
    EditText[] recordArray;
    ArrayList<String> typeArray;
    ArrayList<String> dateArray;
    ArrayList<String> timeArray;
    Context ctxPrevTests = this;
    private ArrayAdapter<String> adapter;
    //Cursor CR;

    public void launchPreviousTests(View view) {
        setContentView(R.layout.activity_previous_tests);
        allRecords = (ListView) findViewById(R.id.recordsListView);

        DatabaseOperations DOP = new DatabaseOperations(ctxPrevTests);
        CR = DOP.getRecordsInfo(DOP);
        //CR.moveToFirst();
        int count = CR.getCount();
        String test = Integer.toString(count);
        //typeArray = new String[count];
        //dateArray = new String[count];
        //timeArray = new String[count];
        recordArray = new EditText[count];
        Toast.makeText(getBaseContext(), test, Toast.LENGTH_LONG).show();

        /*if(CR != null && CR.moveToFirst()) {
            for (int i = 0; i <= (count + 1); i++) {
                typeArray.add(CR.getString(i));
                dateArray.add(CR.getString(i + 1));
                timeArray.add(CR.getString(i + 2));
            }
            CR.close();
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_previous_tests, typeArray);
        allRecords.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

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


