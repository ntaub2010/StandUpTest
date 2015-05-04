package com.naomitaub.standuptest;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class PreviousTestsActivity extends MainActivity implements AdapterView.OnItemClickListener{

    ListView allRecords;
    ArrayList<String> recordArray;
    Context ctxPrevTests = this;
    private ArrayAdapter<String> adapter;
    Cursor CR;
    int rangeInt, highInt, lowInt;
    String dateString, timeString, blurbString;
    Context ctxResults = this;
    public String testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_tests);

        allRecords = (ListView) findViewById(R.id.recordsListView);
        allRecords.setOnItemClickListener(this);

        DatabaseOperations DOP = new DatabaseOperations(ctxPrevTests);
        CR = DOP.getRecordsInfo(DOP);
        CR.moveToFirst();
        int count = CR.getCount();
        recordArray = new ArrayList<String>(count);

        if(CR != null && CR.moveToFirst()) {
            do {
                recordArray.add(("\nTest type: " + CR.getString(0) + "\nDate: " + CR.getString(1)
                        + "\nTime: " + CR.getString(2) + "\n"));
            } while (CR.moveToNext());
            CR.close();
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_text_view_1, recordArray);
        allRecords.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void saveResults(View view) {
        DatabaseOperations DB = new DatabaseOperations(ctxResults);
        DB.putRecordInfo(DB, testType, dateString, timeString, lowInt, highInt, rangeInt, blurbString);

        Toast.makeText(getBaseContext(), "Record insertion successful", Toast.LENGTH_LONG).show();
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        //Intent intent = new Intent();
        //intent.setClass(this, ResultsActivity.class);
        //intent.putExtra("rowPosition", position);
        //startActivity(intent);
        //launchResults(v);
    }
}





