package com.james.workouttracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseLog extends AppCompatActivity {

    // Define ArrayList that will hold the exercises
    ArrayList<Workout> exerciseLogListItems = new ArrayList<Workout>();

    // Define adapter that will handle the exercisesListView
    WorkoutLogListAdapter exerciseLogAdapter;

    String exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log);

        exerciseName = getIntent().getStringExtra("exercise");
        setupListView();
    }

    // Add user inputted log to the list view
    public void addItems(View v) {
        // Retrieve the input that the user entered
        EditText etRep1 = (EditText) findViewById(R.id.rep1);
        EditText etRep2 = (EditText) findViewById(R.id.rep2);
        EditText etRep3 = (EditText) findViewById(R.id.rep3);
        EditText etWeight = (EditText) findViewById(R.id.weight);

        String rep1 = etRep1.getText().toString();
        String rep2 = etRep2.getText().toString();
        String rep3 = etRep3.getText().toString();
        String weight = etWeight.getText().toString();

        // If user doesn't enter a variable, set it to zero
        if (rep1.length() < 1)
            rep1 = "0";
        if (rep2.length() < 1)
            rep2 = "0";
        if (rep3.length() < 1)
            rep3 = "0";
        if (weight.length() < 1)
            weight = "0";

        // Convert to integers
        int iRep1 = Integer.parseInt(rep1);
        int iRep2 = Integer.parseInt(rep2);
        int iRep3 = Integer.parseInt(rep3);
        int iWeight = Integer.parseInt(weight);

        // Create WorkoutLog object
        Workout workoutEntry = new Workout(exerciseName, iRep1, iRep2, iRep3, iWeight);

        exerciseLogListItems.add(workoutEntry);
        exerciseLogAdapter.notifyDataSetChanged();
    }

    // Set up ListView of exercise logs
    private void setupListView() {
        ListView exerciseLogListView = (ListView) findViewById(R.id.exerciseLogListView);
        exerciseLogAdapter = new WorkoutLogListAdapter(this, R.layout.adapter_view_layout, exerciseLogListItems);
        exerciseLogListView.setAdapter(exerciseLogAdapter);

        // Moves to list of sets, reps, and weight when user clicks on exercise
        exerciseLogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // Deletes row if user clicks row for a long time.
        exerciseLogListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                new AlertDialog.Builder(ExerciseLog.this)
                        .setTitle("Delete Log")
                        .setMessage("Are you sure you want to remove this log from your workout journal?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exerciseLogListItems.remove(pos);
                                exerciseLogAdapter.notifyDataSetChanged();
                                Toast.makeText(ExerciseLog.this, "Item deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
