package com.example.covid_19vaccinescheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class CurrentApptList extends AppCompatActivity {

    ListView listView;

    String[] nameArrayA = {"Heather's Appointments:", "Bob's Appointments:"};

    String[] appt1Array = {
            "April 24, 11:30am - 12:00pm",
            "April 24, 11:30am - 12:00pm"
    };

    String[] appt2Array = {
            "May 15, 11:30am - 12:00pm",
            "April 24, 11:30am - 12:00pm"
    };
    String[] nameArray;
    int reschedule_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_appt_list);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int appointmentCount = sharedPref.getInt("ApptCnt", 0);

        ArrayList<String> names_list = new ArrayList<String>();
        List<String> first_date_list = new ArrayList<String>();
        List<String> second_date_list = new ArrayList<String>();
        for (int i = 1; i <= appointmentCount; i++) {
            names_list.add(sharedPref.getString("AName_" + i, "")+ "'s Appointments:");
            first_date_list.add(sharedPref.getString("ADate1_" + i, ""));
            second_date_list.add(sharedPref.getString("ADate2_" + i, ""));
        }

        nameArray = new String[names_list.size()];
        nameArray = names_list.toArray(nameArray);

        String[] date1Array = new String[first_date_list.size()];
        date1Array = first_date_list.toArray(date1Array);

        String[] date2Array = new String[second_date_list.size()];
        date2Array = second_date_list.toArray(date2Array);

        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, date1Array, date2Array);

        listView = (ListView) findViewById(R.id.apptListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button reschedule = (Button) findViewById(R.id.button);
                reschedule_choice = position+1;
                String s = "Reschedule " + nameArray[position];
                reschedule.setText(s);
            }
        });
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void reschedule(View view) {
        Intent intent = new Intent(this, rescheduleActivity.class);
        intent.putExtra("ChangeNum", reschedule_choice);
        startActivity(intent);





    }
}
