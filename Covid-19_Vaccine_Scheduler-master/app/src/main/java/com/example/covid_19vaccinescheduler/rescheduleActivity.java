package com.example.covid_19vaccinescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class rescheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int change_id;
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


        Intent intent = getIntent();
        change_id = intent.getIntExtra("ChangeNum", 0);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = sharedPref.getString("AName_"+change_id, "");
        EditText nameField = findViewById(R.id.editTextTextPersonName);
        nameField.setText(name);

        String age = sharedPref.getString("ANum_"+change_id, "");
        EditText ageField = findViewById(R.id.editTextNumber);
        ageField.setText(age);

        String to_register_address = sharedPref.getString("AAddr_"+change_id, "");
        EditText adddressField = findViewById(R.id.editTextTextPostalAddress);
        adddressField.setText(to_register_address);

        String gender_selection = sharedPref.getString("AGSel_"+change_id, "");
        if (gender_selection.equals("Male")) {
            spinner.setSelection(1);
        } else {
            spinner.setSelection(2);
        }

        String first_date = sharedPref.getString("ADate1_"+change_id, "");
        Button date_1 = findViewById(R.id.button);
        date_1.setText(first_date);

        String second_date = sharedPref.getString("ADate2_"+change_id, "");
        Button date_2 = findViewById(R.id.button2);
        date_2.setText(second_date);
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

    public void selectDateFirst(View view) {

    }

    public void selectDateSecond(View view) {

    }

    public void confirmRegistration(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText nameField = findViewById(R.id.editTextTextPersonName);
        String to_register_name = nameField.getText().toString();
        editor.putString("AName_" + change_id, to_register_name);

        EditText ageField = findViewById(R.id.editTextNumber);
        String to_register_number = ageField.getText().toString();
        editor.putString("ANum_" + change_id, to_register_number);

        EditText adddressField = findViewById(R.id.editTextTextPostalAddress);
        String to_register_address = adddressField.getText().toString();
        editor.putString("AAddr_" + change_id, to_register_address);

        Button date_1 = findViewById(R.id.button);
        String first_date = date_1.getText().toString();
        editor.putString("ADate1_" + change_id, first_date);

        Button date_2 = findViewById(R.id.button2);
        String second_date = date_2.getText().toString();
        editor.putString("ADate2_" + change_id, second_date);

        editor.putString("AGSel_" + change_id, gender_selection);
        editor.apply();

        Intent intent = new Intent(this, CurrentApptList.class);
        startActivity(intent);
    }
}