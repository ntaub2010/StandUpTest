package com.naomitaub.standuptest;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Created by Naomi on 3/7/2015.
 */
/*public class AgeListActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainListView = (ListView)findViewById(R.id.ageListView);

        String[] ages = new String [] {
                "< 10", "11-15", "16-20", "21-25", "26-30", "31-40", "41-50", "51-60", "61-70", "> 70"
        };
        ArrayList<String> ageList = new ArrayList<String>();
        ageList.addAll(Arrays.asList(ages));

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, ageList);

        mainListView.setAdapter(listAdapter);
    }
}*/
