package com.cs40333.cmaheu.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyCsvFileReader reader = new MyCsvFileReader(this);
        ArrayList<String[]> teamstrings = reader.readCsvFile(R.raw.schedule);
        final ArrayList<Team> teams = new ArrayList<>();

        for (int i = 0; i < teamstrings.size(); i++) {
            String[] istring = teamstrings.get(i);
            try {
                teams.add(new Team(istring[0], istring[1], istring[2], istring[3],
                        istring[4], istring[5], istring[6], istring[7], istring[8]));
            } catch (ArrayIndexOutOfBoundsException e) {
                for (int j = 0; j < istring.length; j++) {
                  Log.d("array out of bounds",Integer.toString(j).concat(istring[j]).concat("\n"));
                }
            }
        }


        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, teams);

        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("team", teams.get(position)); //where al is your ArrayList holding team information.
                startActivity(intent);
            }
        };
        scheduleListView.setOnItemClickListener (clickListener);
    }
}
