package com.example.covid_19vaccinescheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EligibilityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eligibility_page);




        // first spinner box :
        Spinner spinner = (Spinner) findViewById(R.id.spinnerExistingConditions);
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("None");
        categories.add("Cancer (currently or at any time in your life)");
        categories.add("COPD (Chronic Obstructive Pulmonary Disease)");
        categories.add("Asthma and other lung problems");
        categories.add("Diabetes (Type I and Type II)");
        categories.add("Heart Condition");
        categories.add("Immunocompromised state");
        categories.add("Kidney disease, chronic");
        categories.add("Obesity (BMI > 30)");
        categories.add("Pregnancy");
        categories.add("Sickle Cell Disease");
        categories.add("Smoking");
        categories.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        // age spinner
        Spinner spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("select");
        categories2.add("0-15");
        categories2.add("16-64");
        categories2.add("65+");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(dataAdapter2);

        // job spinner
        Spinner spinnerOccupation = (Spinner) findViewById(R.id.spinnerOccupation);
        spinnerOccupation.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories3 = new ArrayList<String>();
        categories3.add("Other");
        categories3.add("K-12 public, private, parochail school");
        categories3.add("Grocery Store Worker");
        categories3.add("Food and Agriculture Worker");
        categories3.add("Shelter/Adult Daycare Worker");
        categories3.add("Postage Service / Package Handler Worker");
        categories3.add("Manufacturing Worker");
        categories3.add("Public Transit Worker");
        categories3.add("Medical/Vet Medicine Student");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOccupation.setAdapter(dataAdapter3);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submitEligibilityCheck(View view) {
        TextView submitError = (TextView) findViewById(R.id.errorText);


        Spinner spinnerCondition = (Spinner)findViewById(R.id.spinnerExistingConditions);
        String condition_choice = spinnerCondition.getSelectedItem().toString();
        if (condition_choice == null || condition_choice.equals("select")) {
            submitError.setText("Existing Conditions Required");
            return;
        }

        Spinner spinnerAge = (Spinner)findViewById(R.id.spinnerAge);
        String age_choice = spinnerAge.getSelectedItem().toString();
        if (age_choice == null || age_choice.equals("select")) {
            submitError.setText("Age Required");
            return;
        }

        Spinner spinnerOccupation = (Spinner)findViewById(R.id.spinnerOccupation);
        String occupation_choice = spinnerOccupation.getSelectedItem().toString();
        if (occupation_choice == null || occupation_choice.equals("select")) {
            submitError.setText("Occupation Required");
            return;
        }


        //all spinners have been selected
        submitError.setText("");

        //get checkbox values
        boolean assistedLiving= ((CheckBox) findViewById(R.id.checkboxLearningFacility)).isChecked();
        boolean inChampaign = ((CheckBox) findViewById(R.id.checkboxChampaign)).isChecked();
        boolean noVaccine = ((CheckBox) findViewById(R.id.checkboxRecentVaccine)).isChecked();
        boolean noAntibody = ((CheckBox) findViewById(R.id.checkboxAntibodyInfusion)).isChecked();

        //determine eligibility
        if (age_choice.equals("0-15") || !inChampaign || !noVaccine || !noAntibody) {
            showPopupStatus(false);
        } else if (age_choice.equals("65+") || assistedLiving || !occupation_choice.equals("Other")) {
            showPopupStatus(true);
        } else if (condition_choice.equals("Other")) {
            //TODO
            //this is the additional page one
            Intent intent = new Intent(this, extraConditionsActivity.class);
            startActivity(intent);

        } else if (!condition_choice.equals("none")) {
            showPopupStatus(true);
        } else {
            showPopupStatus(false);
        }
    }


    public void showPopupStatus(Boolean isEligible) {
        if (isEligible) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_popup_eligible);
            ((Button) dialog.findViewById(R.id.buttonCancelDialog))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();

        } else {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_popup_ineligible);
            ((Button) dialog.findViewById(R.id.buttonCloseDialog))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }
    }

    public void registerNow(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}