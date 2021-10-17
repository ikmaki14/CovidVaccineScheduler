package com.example.covid_19vaccinescheduler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity  {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Switch syncCalendar = findViewById(R.id.switch2);
        syncCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syncCalendar.isChecked()) {
                    dialog = new Dialog(SettingsActivity.this);
                    dialog.setContentView(R.layout.email_sync);
                    ((Button) dialog.findViewById(R.id.buttonCancelDialog))
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();
                } else {

                }
            }
        });
    }


    public void privacy(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();
        alertDialog.setTitle("Privacy Statement");
        alertDialog.setMessage("Your data will be sent directly to Carle Clinic. We will not be storing your data.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void storeEmail(View view) {
        EditText emailField = dialog.findViewById(R.id.editTextTextEmailAddress);
        String user_email = emailField.getText().toString();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_email", user_email);
        editor.apply();
        dialog.dismiss();
    }
}


