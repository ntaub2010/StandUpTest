package com.naomitaub.standuptest;

import android.app.Activity;
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
 * Created by Naomi on 5/3/2015.
 */
public class LongTestActivity extends MainActivity {

    Button buttonStartL, buttonPauseL, longEnterHR, viewResults;
    public EditText longHR;
    private TextView timerValueL;

    private long startTimeL = 0L;

    private Handler customHandlerL = new Handler();
    long timeInMillisecondsL = 0L;
    long timeSwapBuffL = 0L;
    long updatedTimeL = 0L;
    public int LDHRL, countL = 0, lowest, range, temp1, temp2, temp3;
    public String testType;
    public String time, date;
    private int longHRArray [] = new int[60], longHRArrayTime [] = new int[60];

    public Calendar cal = new GregorianCalendar();
    public TextView longMessage, longMessageHR;
    public int minsL, secsL;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_test);

        testType = "Long";

        buttonStartL = (Button) findViewById(R.id.startTimerButtonLong);
        buttonPauseL = (Button) findViewById(R.id.stopTimerButtonLong);
        timerValueL = (TextView) findViewById(R.id.textViewTimeLong);
        viewResults = (Button) findViewById(R.id.goToResultsLongButton);
        longHR = (EditText) findViewById(R.id.longHR);
        longEnterHR = (Button) findViewById(R.id.longEnterHRButton);

        date = Integer.toString((cal.get(Calendar.MONTH)) + 1) + "/" +
                Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(cal.get(Calendar.YEAR));

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

        viewResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                launchResults(view, testType, date, time, lowest, temp2, range);
                //enter proper parameters
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
}
