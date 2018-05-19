package com.james.workouttracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutLog.db";

    // Constants for table storing days of week when user works out
    public static final String TABLE_WORKOUT_DAYS = "workout_days_table";
    public static final String WORKOUT_DAYS_COLUMN_ID = "id";
    public static final String WORKOUT_DAYS_COLUMN_DAY = "day";

    // Constants for table storing exercises for each day the user works out
    public static final String TABLE_EXERCISES = "exercises_table";
    public static final String EXERCISES_COLUMN_ID = "id";
    public static final String EXERCISES_COLUMN_NAME = "exercise_name";
    public static final String EXERCISES_COLUMN_WORKOUT_DAYS_ID = "workout_day_id";

    // Constants for table storing log for each exercise
    public static final String TABLE_LOG = "log_table";
    public static final String LOG_COLUMN_ID = "id";
    public static final String LOG_COLUMN_REP_1 = "rep_1";
    public static final String LOG_COLUMN_REP_2 = "rep_2";
    public static final String LOG_COLUMN_REP_3 = "rep_3";
    public static final String LOG_COLUMN_WEIGHT = "weight";
    public static final String LOG_COLUMN_EXERCISES_ID = "exercises_id";

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for workout days
        String dayOfWeekQuery = "CREATE TABLE " + TABLE_WORKOUT_DAYS + "(" +
                WORKOUT_DAYS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_DAYS_COLUMN_DAY + " TEXT " +
                ");";
        db.execSQL(dayOfWeekQuery);

        // Create table for exercises
        String exercisesQuery = "CREATE TABLE " + TABLE_EXERCISES + "(" +
                EXERCISES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXERCISES_COLUMN_NAME + " TEXT, " +
                EXERCISES_COLUMN_WORKOUT_DAYS_ID + " INTEGER, " +
                "FOREIGN KEY (" + EXERCISES_COLUMN_WORKOUT_DAYS_ID + ") REFERENCES " +
                    TABLE_WORKOUT_DAYS + "(" + WORKOUT_DAYS_COLUMN_ID + ")" + " ON DELETE CASCADE " +
                ");";
        db.execSQL(exercisesQuery);

        // Create table for log
        String logQuery = "CREATE TABLE " + TABLE_LOG + "(" +
                LOG_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LOG_COLUMN_REP_1 + " INTEGER, " +
                LOG_COLUMN_REP_2 + " INTEGER, " +
                LOG_COLUMN_REP_3 + " INTEGER, " +
                LOG_COLUMN_WEIGHT + " INTEGER, " +
                LOG_COLUMN_EXERCISES_ID + " INTEGER, " +
                "FOREIGN KEY (" + LOG_COLUMN_EXERCISES_ID + ") REFERENCES " +
                    TABLE_EXERCISES + "(" + EXERCISES_COLUMN_ID + ")" + " ON DELETE CASCADE " +
                ");";
        db.execSQL(logQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table for workout days
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_DAYS);

        // Drop table for exercises
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);

        // Drop table for log
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);

        onCreate(db);
    }

    // ------------------------------------------------------------------------------------------ //
    // DATABASE METHODS FOR WORKOUT DAYS

    // Add a day of week that user works out to database
    public void addWorkoutDay(String day) {
        ContentValues values = new ContentValues();
        values.put(WORKOUT_DAYS_COLUMN_DAY, day);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WORKOUT_DAYS, null, values);
        db.close();
    }

    // Delete a workout day from the database
    public void deleteWorkoutDay(String day){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WORKOUT_DAYS + " WHERE " + WORKOUT_DAYS_COLUMN_DAY + "=\"" + day + "\";");
    }

    // Get the days of week that the user works out as an ArrayList
    public ArrayList<String> getWorkoutDays() {
        // Query database for workout days
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUT_DAYS;
        Cursor result = db.rawQuery(query, null);

        // Populate ArrayList with workout days data
        ArrayList<String> workoutDaysList = new ArrayList<String>();
        int dayIndex = result.getColumnIndex(WORKOUT_DAYS_COLUMN_DAY);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            if (result.getString(dayIndex) != null) {
                workoutDaysList.add(result.getString(dayIndex));
            }
            result.moveToNext();
        }

        db.close();

        return workoutDaysList;
    }

    // Given a dayOfWeek, return the id associated with the dayOfWeek if it exists, otherwise return -1.
    public int getWorkoutDayId(String workoutDay) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUT_DAYS + " WHERE " + WORKOUT_DAYS_COLUMN_DAY + "=\"" + workoutDay + "\";";
        Cursor result = db.rawQuery(query, null);

        result.moveToFirst();

        int idIndex = result.getColumnIndex(WORKOUT_DAYS_COLUMN_ID);
        int id = result.getInt(idIndex);
        db.close();

        return id;
    }


    // ------------------------------------------------------------------------------------------ //
    // DATABASE METHODS FOR EXERCISES

    // Add an exercise that user does on a particular dayOfWeek to database
    public void addExercise(String exerciseName, int workoutDayId) {
        ContentValues values = new ContentValues();
        values.put(EXERCISES_COLUMN_NAME, exerciseName);
        values.put(EXERCISES_COLUMN_WORKOUT_DAYS_ID, workoutDayId);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    // Delete an exercise from the database
    public void deleteExercise(int exerciseId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXERCISES + " WHERE " + EXERCISES_COLUMN_ID + "=\"" + exerciseId + "\";");
    }

    // Get the exercises the user does on a particular dayOfWeek as an ArrayList
    public ArrayList<String> getExercises(int workoutDayId) {
        // Query database for exercises
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE " + EXERCISES_COLUMN_WORKOUT_DAYS_ID + "=\"" + workoutDayId + "\";";
        Cursor result = db.rawQuery(query, null);

        // Populate ArrayList with exercise data
        ArrayList<String> exercisesList = new ArrayList<String>();
        int exerciseIndex = result.getColumnIndex(EXERCISES_COLUMN_NAME);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            if (result.getString(exerciseIndex) != null) {
                exercisesList.add(result.getString(exerciseIndex));
            }
            result.moveToNext();
        }

        db.close();

        return exercisesList;
    }

    // Return the id associated with the exerciseName and workoutDayId
    public int getExerciseId(String exerciseName, int workoutDayId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE " +
                EXERCISES_COLUMN_NAME + "=\"" + exerciseName + "\" AND " +
                EXERCISES_COLUMN_WORKOUT_DAYS_ID + "=\"" + workoutDayId + "\";";
        Cursor result = db.rawQuery(query, null);

        result.moveToFirst();

        int idIndex = result.getColumnIndex(EXERCISES_COLUMN_ID);
        int id = result.getInt(idIndex);
        db.close();

        return id;
    }

    // ------------------------------------------------------------------------------------------ //
    // DATABASE METHODS FOR WORKOUT LOG
}
