package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.data.AddTaskDbHelper;

/**
 * Created by steffichoi on 8/18/17.
 */

public class TaskDetailFragment extends Fragment {

    View myView;
    private SQLiteDatabase mDb;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_detail_layout, container, false);

        Bundle bundle = getArguments();
        String taskName = "";
        if (bundle != null) {
            taskName= bundle.getString("task_name", "no task");
        }

        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getTask(taskName);

        TextView taskMonthTv = (TextView) myView.findViewById(R.id.detail_month_tv);
        TextView taskDateTv = (TextView) myView.findViewById(R.id.detail_date_tv);
        TextView taskNameTv = (TextView) myView.findViewById(R.id.detail_item_name_tv);
        TextView taskTimeTv = (TextView) myView.findViewById(R.id.detail_item_time_tv);
        TextView taskDurationTv = (TextView) myView.findViewById(R.id.detail_item_duration_tv);
        TextView taskLocationTv = (TextView) myView.findViewById(R.id.detail_item_location_tv);
        TextView taskTypeTv = (TextView) myView.findViewById(R.id.detail_item_type_tv);
        TextView taskReminderTv = (TextView) myView.findViewById(R.id.detail_item_reminder_tv);

        String monthDateFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_DATE));
        String dateDb = monthDateFromDb.split("/")[0];
        int monthDb = Integer.parseInt(monthDateFromDb.split("/")[1]);
        String monthFin = new DateFormatSymbols().getMonths()[monthDb];;


        String nameFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        String timeFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME));
        String durationFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_DURATION));
        String locationFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_LOCATION));
//        String typeFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TYPE));
//        String reminderFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_REMINDER));

        taskNameTv.setText(nameFromDb);
        taskMonthTv.setText(monthFin);
        taskDateTv.setText(dateDb);
        taskTimeTv.setText("Start   \t\t\t: " + timeFromDb);
        taskDurationTv.setText("End   \t\t\t   : " + durationFromDb);
        taskLocationTv.setText("Location \t: " + locationFromDb);
        taskTypeTv.setText("Type   \t\t\t  : ");
        taskReminderTv.setText("Reminder \t: ");

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.detail_edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperFunctions.switchMainContentFragment(new AddTaskFragment(), getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());

            }
        });


        return myView;
    }

}
