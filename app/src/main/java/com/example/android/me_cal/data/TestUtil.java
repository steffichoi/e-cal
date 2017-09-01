package com.example.android.me_cal.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME, "26082017100000");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME, "breakfast");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_LOCATION, "home");
        list.add(cv);

        cv = new ContentValues();
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME, "26/08/2017 13:00:00");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME, "lunch");
        list.add(cv);

        cv = new ContentValues();
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME, "26/08/2017 15:00:00");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME, "snack");
        list.add(cv);

        cv = new ContentValues();
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME, "26/08/2017 18:00:00");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME, "dinner");
        list.add(cv);

        cv = new ContentValues();
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME, "26/08/2017 22:00:00");
        cv.put(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME, "supper");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (AddTaskContract.AddTaskEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(AddTaskContract.AddTaskEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}