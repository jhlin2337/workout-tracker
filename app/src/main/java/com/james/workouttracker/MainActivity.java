package com.james.workouttracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Define ArrayList that will hold the workoutDays
    ArrayList<String> workoutDaysListItems = new ArrayList<String>();

    // Define adapter that will handle the workoutDaysListView
    ArrayAdapter<String> workoutDaysAdapter;

    // Day of week that's displayed on the dropdown
    String selectedDay = "Monday";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSpinner();
        setupListView();
    }

    // Handle item user selected from spinner to ListView
    public void addItems(View v) {
        if (workoutDaysListItems.indexOf(selectedDay) == -1) {
            workoutDaysListItems.add(selectedDay);
        }
        workoutDaysAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        selectedDay = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Setup days of week dropdown selector
    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.daysOfWeekSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daysOfWeekArray, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    // Set up ListView of days of week when user works out
    private void setupListView() {
        ListView workoutDaysListView = (ListView) findViewById(R.id.workoutDaysListView);
        workoutDaysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, workoutDaysListItems);
        workoutDaysListView.setAdapter(workoutDaysAdapter);

        // Moves to workout page when user clicks on day of week
        workoutDaysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // Deletes row if user clicks row for a long time.
        workoutDaysListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Workout Day")
                        .setMessage("Are you sure you want to remove this day of the week from your workout journal?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                workoutDaysListItems.remove(pos);
                                workoutDaysAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
