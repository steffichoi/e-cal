package com.example.android.me_cal.data;

/**
 * Created by steffichoi on 8/23/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.me_cal.data.AddTaskContract.*;

public class AddTaskDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";
    static final int DATABASE_VERSION = 1;

    // Constructor
    public AddTaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                AddTaskEntry.TABLE_NAME + " (" +
                AddTaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddTaskEntry.COLUMN_TASK_NAME+ " TEXT NOT NULL, " +
//                AddTaskEntry.COLUMN_TASK_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                AddTaskEntry.COLUMN_TASK_DATE + " TEXT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_TIME + " TEXT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_DURATION + " INTEGER DEFAULT 30, " +
                AddTaskEntry.COLUMN_TASK_LOCATION + " TEXT DEFAULT NULL " +

                "); ";

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AddTaskContract.AddTaskEntry.TABLE_NAME);
        onCreate(db);
    }


    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                AddTaskContract.AddTaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME
        );
    }

    public long addEvent(String name, String date, String time, String duration, String location) {
        SQLiteDatabase mDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AddTaskEntry.COLUMN_TASK_NAME, name);
        cv.put(AddTaskEntry.COLUMN_TASK_DATE, date);
        cv.put(AddTaskEntry.COLUMN_TASK_TIME, time);
        cv.put(AddTaskEntry.COLUMN_TASK_DURATION, duration);
        cv.put(AddTaskEntry.COLUMN_TASK_LOCATION, location);

        return mDb.insert(AddTaskEntry.TABLE_NAME, null, cv);
    }
}
