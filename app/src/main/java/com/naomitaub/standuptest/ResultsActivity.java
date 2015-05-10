package com.naomitaub.standuptest;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends MainActivity {

    public static TextView resultsDate, resultsTime, resultsBlurb, resultsRange, resultsHigh, resultsLow;
    String email, blurbString, date, time, testType;
    int lowest, range, temp2;
    Context ctx = this;
    Button saveResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        testType = intent.getStringExtra("testType");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        lowest = intent.getIntExtra("lowest", 0);
        temp2 = intent.getIntExtra("highest", 0);
        range = intent.getIntExtra("range", 0);

        saveResultsButton = (Button) findViewById(R.id.saveResultsButton);



        resultsRange = (TextView) findViewById(R.id.resultsRangeVal);
        resultsHigh = (TextView) findViewById(R.id.resultsHighVal);
        resultsLow = (TextView) findViewById(R.id.resultsLowVal);
        resultsDate = (TextView) findViewById(R.id.resultsDate);
        resultsTime = (TextView) findViewById(R.id.resultsTime);
        resultsBlurb = (TextView) findViewById(R.id.resultsBlurb);

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

        blurbString = (resultsBlurb.getText()).toString();

        saveResultsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseOperations DB = new DatabaseOperations(ctx);
                DB.putRecordInfo(DB, email, testType, date, time, lowest, temp2, range, blurbString);

                Toast.makeText(getBaseContext(), "Record insertion successful", Toast.LENGTH_LONG).show();
            }
        });
    }

}
