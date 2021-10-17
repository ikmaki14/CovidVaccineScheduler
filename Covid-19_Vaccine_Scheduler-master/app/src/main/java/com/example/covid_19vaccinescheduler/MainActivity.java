package com.example.covid_19vaccinescheduler;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.date_1);
        //datePicker();
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void checkEligibility(View view) {
        Intent intent = new Intent(this, EligibilityActivity.class);
        startActivity(intent);
    }

    public void viewAppointments(View view) {
        Intent intent = new Intent(this, CurrentApptList.class);
        startActivity(intent);
    }

    public void datePicker() {
        Intent intent = new Intent(this, date2Activity.class);
        startActivity(intent);
    }
}