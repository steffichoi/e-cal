package com.example.android.me_cal.data;

/**
 * Created by steffichoi on 8/23/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.data.AddTaskContract.*;

import java.text.SimpleDateFormat;

public class AddTaskDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";
    static final int DATABASE_VERSION = 1;
    private Context mContext;

    // Constructor
    public AddTaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                AddTaskEntry.TABLE_NAME + " (" +
                AddTaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddTaskEntry.COLUMN_TASK_NAME+ " TEXT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_DATE + " INT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_TIME_START + " INT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_TIME_END + " INT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_LOCATION + " TEXT DEFAULT NULL, " +
                AddTaskEntry.COLUMN_TASK_TYPE + " TEXT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_REMINDER + " TEXT NOT NULL, " +
                AddTaskEntry.COLUMN_TASK_CUSTOM_REMINDER + " INT NOT NULL, " +
                AddTaskEntry.CUSTOM_TASK_ALARM_ID + " INT NOT NULL" +
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
                AddTaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                AddTaskEntry.COLUMN_TASK_TIME_START);
    }

    public Cursor getTask(long taskTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + AddTaskEntry.TABLE_NAME + " WHERE "
                + AddTaskEntry.COLUMN_TASK_TIME_START + "='" + taskTime + "'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getDay(long taskDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + AddTaskEntry.TABLE_NAME + " WHERE "
                + AddTaskEntry.COLUMN_TASK_DATE + "='" + taskDate + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) cursor.moveToFirst();

        return cursor;
    }

    public long addEvent(String name, long date, long timeStart, long timeEnd,
                         String location, String type, String reminder, long customReminder, int alarmID) {
        SQLiteDatabase mDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AddTaskEntry.COLUMN_TASK_NAME, name);
        cv.put(AddTaskEntry.COLUMN_TASK_DATE, date);
        cv.put(AddTaskEntry.COLUMN_TASK_TIME_START, timeStart);
        cv.put(AddTaskEntry.COLUMN_TASK_TIME_END, timeEnd);
        cv.put(AddTaskEntry.COLUMN_TASK_LOCATION, location);
        cv.put(AddTaskEntry.COLUMN_TASK_TYPE, type);
        cv.put(AddTaskEntry.COLUMN_TASK_REMINDER, reminder);
        cv.put(AddTaskEntry.COLUMN_TASK_CUSTOM_REMINDER, customReminder);
        cv.put(AddTaskEntry.CUSTOM_TASK_ALARM_ID, alarmID);

        return mDb.insert(AddTaskEntry.TABLE_NAME, null, cv);
    }

    public void update (String name, long date, long timeStart, long timeEnd,
                        String location, String type, String reminder, long customReminder, int alarmID, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AddTaskEntry.COLUMN_TASK_NAME, name);
        cv.put(AddTaskEntry.COLUMN_TASK_DATE, date);
        cv.put(AddTaskEntry.COLUMN_TASK_TIME_START, timeStart);
        cv.put(AddTaskEntry.COLUMN_TASK_TIME_END, timeEnd);
        cv.put(AddTaskEntry.COLUMN_TASK_LOCATION, location);
        cv.put(AddTaskEntry.COLUMN_TASK_TYPE, type);
        cv.put(AddTaskEntry.COLUMN_TASK_REMINDER, reminder);
        cv.put(AddTaskEntry.COLUMN_TASK_CUSTOM_REMINDER, customReminder);
        cv.put(AddTaskEntry.CUSTOM_TASK_ALARM_ID, alarmID);

        db.update(AddTaskEntry.TABLE_NAME, cv, AddTaskEntry._ID + "=" + id, null);
    }

    public boolean removeGuest(long id) {
        SQLiteDatabase mDb = this.getWritableDatabase();
        return mDb.delete(AddTaskEntry.TABLE_NAME,
                AddTaskEntry._ID + "=" + id, null) > 0;
    }

    public int getAlarmId(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + AddTaskEntry.TABLE_NAME + " WHERE "
                + AddTaskEntry._ID + "='" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(AddTaskEntry.CUSTOM_TASK_ALARM_ID));
    }
}
