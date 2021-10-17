package com.example.covid_19vaccinescheduler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //public Button dt_btn = findViewById(R.id.button);
    Button dt_btn;
    String text;
    boolean setDateFirst = false;
    boolean setDateSecond = false;

    String to_register_name;
    String to_register_number;
    String to_register_address;
    String first_date = "Select Date";
    String second_date = "Select Date";
    long firstDateLong, secondDateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("select");
        categories.add("Male");
        categories.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        to_register_name = sharedPref.getString("RName", "");
        to_register_number = sharedPref.getString("RNum", "");
        to_register_address = sharedPref.getString("RAddr", "");
        first_date = sharedPref.getString("Date1", "Select Date");
        second_date = sharedPref.getString("Date2", "Select Date");
        gender_selection = sharedPref.getString("GSel", "");
        gender_position = sharedPref.getInt("GPos", 0);
        firstDateLong = sharedPref.getLong("LongDate", 0);


        //written values
        spinner.setSelection(gender_position);

        EditText name =  (EditText) findViewById(R.id.editTextTextPersonName);
        name.setText(to_register_name);

        EditText number =  (EditText) findViewById(R.id.editTextNumber);
        number.setText(to_register_number);

        EditText address =  (EditText) findViewById(R.id.editTextTextPostalAddress);
        address.setText(to_register_address);

        if (first_date != null) {
            dt_btn = findViewById(R.id.button);
            dt_btn.setText(first_date);
        }

        if (second_date != null) {
            dt_btn = findViewById(R.id.button2);
            dt_btn.setText(second_date);
        }

        //dates
        //get intent values
        Intent intent_time = getIntent();
        setDateFirst = intent_time.getBooleanExtra(date1Activity.EXTRA_BOOL_P,false);
        setDateSecond = intent_time.getBooleanExtra(date2Activity.EXTRA_BOOL_2P, false);
        if (setDateFirst) {
            dt_btn = findViewById(R.id.button);
            intent_time = getIntent();
            text = intent_time.getStringExtra(date1Activity.EXTRA_TIME_P);
            firstDateLong = intent_time.getLongExtra("LongDate", 0);
            dt_btn.setText(text);
            first_date = text;
        }
        if (setDateSecond) {
            dt_btn = findViewById(R.id.button2);
            intent_time = getIntent();
            text = intent_time.getStringExtra(date2Activity.EXTRA_TIME_2P);
            secondDateLong = intent_time.getLongExtra("SecondDateMilliS", 0);
            dt_btn.setText(text);
            second_date = text;
        }
    }

    String gender_selection;
    int gender_position;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        gender_position = position;
        gender_selection = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void myPause() {
        EditText name =  (EditText) findViewById(R.id.editTextTextPersonName);
        to_register_name = name.getText().toString();

        EditText number =  (EditText) findViewById(R.id.editTextNumber);
        to_register_number = number.getText().toString();

        EditText address =  (EditText) findViewById(R.id.editTextTextPostalAddress);
        to_register_address = address.getText().toString();


        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("RName", to_register_name);
        editor.putString("RNum", to_register_number);
        editor.putString("RAddr", to_register_address);
        editor.putString("Date1", first_date);
        editor.putString("Date2", second_date);
        editor.putString("GSel", gender_selection);
        editor.putInt("GPos", gender_position);
        editor.putLong("LongDate", firstDateLong);
        editor.putLong("SecondLongDate", secondDateLong);
        editor.apply();

    }

    public void selectDateFirst(View view) {
        myPause();
        Intent intent = new Intent(this, date1Activity.class);
        startActivity(intent);
    }

    public void selectDateSecond(View view) {
        if (first_date != null) {
            myPause();
            Intent intent = new Intent(this, date2Activity.class);
            intent.putExtra("LongDate", firstDateLong);
            startActivity(intent);
        }
    }

    public void goHome(View view) {
        clearData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveAppointment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int appointmentCount = sharedPreferences.getInt("ApptCnt", 0) + 1;

        SharedPreferences.Editor editor = sharedPreferences.edit();

        //SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ApptCnt", appointmentCount);
        String count = String.valueOf(appointmentCount);

        editor.putString("AName_" + count, to_register_name);
        editor.putString("ANum_" + count, to_register_number);
        editor.putString("AAddr_" + count, to_register_address);
        editor.putString("ADate1_" + count, first_date);
        editor.putString("ADate2_" + count, second_date);
        editor.putString("AGSel_" + count, gender_selection);

        editor.apply();

        clearData();
    }

    public void clearData() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //clear out values
        editor.putString("RName", "");
        editor.putString("RNum", "");
        editor.putString("RAddr", "");
        editor.putString("Date1", "Select Date");
        editor.putString("Date2", "Select Date");
        editor.putString("GSel", "");
        editor.putInt("GPos", 0);

        editor.apply();
    }

    public void confirmRegistration(View view) {
        myPause();
        TextView errorMessage = findViewById(R.id.errorText);

        if (first_date.equals("Select Date") || second_date.equals("Select Date")) {
            errorMessage.setText("Please Select Appointment Dates");
        } else if (to_register_name.equals("")) {
            errorMessage.setText("Name Required");
        } else if (to_register_number.equals("")) {
            errorMessage.setText("Age Required");
        } else if (gender_position == 0) {
            errorMessage.setText("Gender Required");
        } else if (to_register_address.equals("")) {
            errorMessage.setText("Address Required");
        } else {
            saveAppointment(); //not fully functional
            Intent intent = new Intent(this, completeActivity.class);
            intent.putExtra("SecondDateMilliS", secondDateLong);
            startActivity(intent);
        }
    }
}