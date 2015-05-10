package com.naomitaub.standuptest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Naomi on 3/8/2015.
 */
public class ShortTestActivity extends MainActivity {

    Button buttonStartS, buttonPauseS, shortEnterHR, viewResults;
    public EditText shortHR;
    private TextView timerValueS;

    String email;
    private long startTimeS = 0L;

    private Handler customHandlerS = new Handler();

    public String time, date, testType;
    long timeInMillisecondsS = 0L, timeSwapBuffS = 0L, updatedTimeS = 0L;
    public int LDHRS, countS = 0, minsS, secsS, lowest, range, temp1, temp2, temp3;
    private int shortHRArray [] = new int[10], shortHRArrayTime [] = new int[10];

    public TextView shortMessage, shortMessageHR;
    public Calendar cal = new GregorianCalendar();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_test);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        testType = "Short";

        buttonStartS = (Button) findViewById(R.id.startTimerButtonShort);
        buttonPauseS = (Button) findViewById(R.id.stopTimerButtonShort);
        timerValueS = (TextView) findViewById(R.id.textViewTimeShort);
        viewResults = (Button) findViewById(R.id.goToResultsShortButton);

        shortHR = (EditText) findViewById(R.id.shortHR);
        shortEnterHR = (Button) findViewById(R.id.shortEnterHRButton);

        date = Integer.toString((cal.get(Calendar.MONTH)) + 1) + "/" +
                Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(cal.get(Calendar.YEAR));

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

        viewResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                launchResults(view, email, testType, date, time, lowest, temp2, range);
                //enter proper parameters
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
}