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
 * Created by Naomi on 3/8/2015.
 */
public class MediumTestActivity extends MainActivity {

    Button buttonStartM, buttonPauseM, medEnterHR, viewResults;
    public EditText medHR;
    private TextView timerValueM;

    private long startTimeM = 0L;

    private Handler customHandlerM = new Handler();
    long timeInMillisecondsM = 0L;
    long timeSwapBuffM = 0L;
    long updatedTimeM = 0L;
    public int LDHRM, countM = 0, lowest, range, temp1, temp2, temp3;
    public String testType;
    public String time, date;
    private int medHRArray [] = new int[30], medHRArrayTime [] = new int[30];

    public Calendar cal = new GregorianCalendar();
    public TextView medMessage, medMessageHR;
    public int minsM, secsM;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_test);

        testType = "Medium";

        buttonStartM = (Button) findViewById(R.id.startTimerButtonMedium);
        buttonPauseM = (Button) findViewById(R.id.stopTimerButtonMedium);
        timerValueM = (TextView) findViewById(R.id.textViewTimeMedium);
        viewResults = (Button) findViewById(R.id.goToResultsMedButton);

        medHR = (EditText) findViewById(R.id.medHR);
        medEnterHR = (Button) findViewById(R.id.medEnterHRButton);

        date = Integer.toString((cal.get(Calendar.MONTH)) + 1) + "/" +
                Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(cal.get(Calendar.YEAR));

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
                medHRArrayTime[countM] = temp;
                countM++;
                medHR.setText("");
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
};


