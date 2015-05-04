package com.naomitaub.standuptest;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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
}
