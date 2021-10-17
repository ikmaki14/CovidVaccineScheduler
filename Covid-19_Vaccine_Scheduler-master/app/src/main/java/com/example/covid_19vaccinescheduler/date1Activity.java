package com.example.covid_19vaccinescheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class date1Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView appointment;
    private Spinner tPick;

    public String appointmentDate;
    public String appointmentTime;

    public long dateChosenLong;

    public static final String EXTRA_TIME_P = "EXTRA_TIME_P";
    public static final String EXTRA_BOOL_P = "EXTRA_BOOL_P";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_selection);

        Calendar calendar = Calendar.getInstance();

        CalendarView calView = (CalendarView) findViewById(R.id.calendarView);
        appointment = (TextView)findViewById(R.id.appointment);
        tPick = (Spinner) findViewById(R.id.spinnerChooseTime);
        tPick.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("select appointment time");
        categories.add("10:15 am");
        categories.add("10:30 am");
        categories.add("11:30 am");
        categories.add("11:45 am");
        categories.add("12:00 pm");
        categories.add("12:15 pm");
        categories.add("12:30 pm");
        categories.add("12:45 pm");
        categories.add("1:00 pm");
        categories.add("1:15 pm");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tPick.setAdapter(dataAdapter);

        Button dButton = (Button) findViewById(R.id.button5);

        long currentDate = calendar.getTimeInMillis();
        long yearAfter = currentDate + (1000L*60*60*24*365);
        calView.setMinDate(currentDate);
        calView.setMaxDate(yearAfter); // have a year to sign up

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                appointmentDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                appointment.setText("Appointment Date: " + appointmentDate);

                calendar.set(year, month, dayOfMonth);
                dateChosenLong = calendar.getTimeInMillis();

                tPick.setVisibility(View.VISIBLE);
            }
        });

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentTime = tPick.getSelectedItem().toString();

                if (appointmentDate != null && !appointmentTime.equals("select appointment time")) {
                    saveDate();
                }
            }
        });
    }


    public void saveDate() {
        onPause();
        Intent intent = new Intent(this, RegisterActivity.class);
        String appointment_display = appointmentDate + " at " + appointmentTime;

        intent.putExtra(EXTRA_TIME_P, appointment_display);
        intent.putExtra(EXTRA_BOOL_P, true);
        intent.putExtra("LongDate", dateChosenLong);
        startActivity(intent);
    }

    public void cancelDate(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}