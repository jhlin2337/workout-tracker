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

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_WORKOUT_DAYS + "(" +
                WORKOUT_DAYS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_DAYS_COLUMN_DAY + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_DAYS);
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

    // Get the days of week that the user works out as an arraylist
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


    // ------------------------------------------------------------------------------------------ //
    // DATABASE METHODS FOR EXERCISES


    // ------------------------------------------------------------------------------------------ //
    // DATABASE METHODS FOR WORKOUT LOG
}
