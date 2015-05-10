package com.naomitaub.standuptest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class PreviousTestsActivity extends MainActivity implements AdapterView.OnItemClickListener{

    ListView allRecords;
    ArrayList<String> recordArray;
    Context ctxPrevTests = this;
    private ArrayAdapter<String> adapter;
    Cursor CR;
    int lowest, temp2, range;
    String time, date, email;
    public String testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_tests);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        allRecords = (ListView) findViewById(R.id.recordsListView);
        allRecords.setOnItemClickListener(this);

        DatabaseOperations DOP = new DatabaseOperations(ctxPrevTests);
        CR = DOP.getRecordsInfo(DOP, email);
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

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        DatabaseOperations DOP = new DatabaseOperations(ctxPrevTests);
        CR = DOP.getTestInfo(DOP, email);

        CR.moveToPosition(position);
        testType = CR.getString(0);
        date = CR.getString(1);
        time = CR.getString(2);
        lowest = CR.getInt(3);
        temp2 = CR.getInt(4);
        range = CR.getInt(5);
        launchResults(v, email, testType, date, time, lowest, temp2, range);
    }
}





