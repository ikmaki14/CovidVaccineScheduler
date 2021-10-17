package com.example.covid_19vaccinescheduler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] nameArray;
    private final String[] appt1Array;
    private final String[] appt2Array;

    public CustomListAdapter(Activity context, String[] nameArrayParam, String[] appt1ArrayParam, String[] appt2ArrayParam){

        super(context, R.layout.listview_row , nameArrayParam);

        this.context = context;
        this.nameArray = nameArrayParam;
        this.appt1Array = appt1ArrayParam;
        this.appt2Array = appt2ArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        // gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView appt1TextField = (TextView) rowView.findViewById(R.id.appt1TextView);
        TextView appt2TextField = (TextView) rowView.findViewById(R.id.appt2TextView);

        // sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        appt1TextField.setText(appt1Array[position]);
        appt2TextField.setText(appt2Array[position]);

        return rowView;
    };
} // https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
