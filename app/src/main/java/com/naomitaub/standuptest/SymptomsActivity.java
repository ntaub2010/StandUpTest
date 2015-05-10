package com.naomitaub.standuptest;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class SymptomsActivity extends MainActivity {

    public TextView symptomsBL;
    public TextView symptomsLinkDI, symptomsLinkDINET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        symptomsBL = (TextView) findViewById(R.id.symptomsBL);
        symptomsBL.setText(Html.fromHtml(getString(R.string.symptoms)));

        symptomsLinkDI = (TextView) findViewById(R.id.dysautonomiaInternationalLinkTextView);
        symptomsLinkDINET = (TextView) findViewById(R.id.dinetSymptomsLinkTextView);

        symptomsLinkDI.setMovementMethod(LinkMovementMethod.getInstance());
        symptomsLinkDINET.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
