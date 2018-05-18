package com.james.workouttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {

    // Define ArrayList that will hold the exercises
    ArrayList<String> exercisesListItems = new ArrayList<String>();

    // Define adapter that will handle the exercisesListView
    ArrayAdapter<String> exercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        String dayOfWeek = getIntent().getStringExtra("dayOfWeek");
        setupListView();
    }

    // Add user inputted exercise to the list view
    public void addItems(View v) {
        EditText exerciseNameEditText = (EditText) findViewById(R.id.exerciseName);
        String exerciseName = exerciseNameEditText.getText().toString();

        if (exerciseName.length() > 0 && exercisesListItems.indexOf(exerciseName) == -1) {
            exercisesListItems.add(exerciseName);
        }
        exercisesAdapter.notifyDataSetChanged();
    }

    // Set up ListView of days of week when user works out
    private void setupListView() {
        ListView exercisesListView = (ListView) findViewById(R.id.exercisesListView);
        exercisesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exercisesListItems);
        exercisesListView.setAdapter(exercisesAdapter);

        // Moves to list of sets, reps, and weight when user clicks on exercise
        exercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ExerciseLog.class);
                intent.putExtra("exercise", exercisesListItems.get(position));
                startActivity(intent);
            }
        });

        // Deletes row if user clicks row for a long time.
        exercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                new AlertDialog.Builder(Exercises.this)
                        .setTitle("Delete Exercise")
                        .setMessage("Are you sure you want to remove this exercise from your workout journal?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exercisesListItems.remove(pos);
                                exercisesAdapter.notifyDataSetChanged();
                                Toast.makeText(Exercises.this, "Item deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
