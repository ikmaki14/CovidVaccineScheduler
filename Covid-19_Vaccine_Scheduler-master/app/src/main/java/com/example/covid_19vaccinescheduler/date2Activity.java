package com.example.covid_19vaccinescheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class date2Activity extends AppCompatActivity {

    private TextView appointment;
    private Spinner tPick;

    public String appointmentDate;
    public String appointmentTime;

    public long dateChosenLong;
    int c_year, c_month, c_day;


    public static final String EXTRA_TIME_2P = "EXTRA_TIME_2P";
    public static final String EXTRA_BOOL_2P = "EXTRA_BOOL_2P";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_selection);
        Calendar calendar = Calendar.getInstance();

        CalendarView calView = (CalendarView) findViewById(R.id.calendarView);
        appointment = (TextView)findViewById(R.id.appointment);
        tPick = (Spinner)findViewById(R.id.spinnerChooseTime);
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

        Intent intent_time = getIntent();
        long firstDateLong = intent_time.getLongExtra("LongDate", 0);
        long minDate = firstDateLong + (21 * 86400000);
        long maxDate = minDate + (2 * 86400000);
        calView.setMinDate(minDate);
        calView.setMaxDate(maxDate);


        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                appointmentDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                appointment.setText("Appointment Date: " + appointmentDate);

                c_year = year;
                c_month = month;
                c_day = dayOfMonth;

                tPick.setVisibility(View.VISIBLE);
            }
        });

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentTime = tPick.getSelectedItem().toString();

                if (appointmentDate != null && !appointmentTime.equals("select appointment time")) {

                    int hour = Integer.parseInt(appointmentTime.substring(0,2));
                    int minute = Integer.parseInt(appointmentTime.substring(3, 5));

                    calendar.set(c_year, c_month, c_day, hour, minute);
                    dateChosenLong = calendar.getTimeInMillis();
                    saveDate();
                }
            }
        });
    }

    public void saveDate() {
        onPause();
        Intent intent = new Intent(this, RegisterActivity.class);
        String appointment_display = appointmentDate + " at " + appointmentTime;
        intent.putExtra(EXTRA_TIME_2P, appointment_display);
        intent.putExtra(EXTRA_BOOL_2P, true);
        intent.putExtra("SecondDateMilliS", dateChosenLong);
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
}