package com.example.covid_19vaccinescheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class completeActivity extends AppCompatActivity {

    String title;
    String location;
    String description;
    Button syncCalendar;
    Long secondDateLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete);
        Intent intent_time = getIntent();
        secondDateLong = intent_time.getLongExtra("SecondDateMilliS", 0);
        Long secondDateEnd = secondDateLong + (1000 * 60 * 15);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_email = sharedPref.getString("user_email", "");

        title = "First Covid Vaccine Appt";
        location = "Carle Clinic";
        description = "";
        syncCalendar = findViewById(R.id.addCal);

        syncCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, title);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, secondDateLong);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, secondDateEnd);
                intent.putExtra(Intent.EXTRA_EMAIL, user_email);

                if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                } else {
                    Toast.makeText(completeActivity.this, "There is no app that works :(",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerAgain(View view) {
        Intent intent = new Intent(this, EligibilityActivity.class);
        startActivity(intent);
    }

    public void doneRegistering(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    static long getMiliseconds(String date, String time) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
            Date date1 = sdf.parse(date+" "+time);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar beginCal = Calendar.getInstance();

            beginCal.set(cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH), cal1.get(Calendar.HOUR_OF_DAY), cal1.get(Calendar.MINUTE));
            return beginCal.getTimeInMillis();
        }
        catch (Exception e) {
            return new Date().getTime();
        }
    }

}