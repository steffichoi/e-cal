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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.data.AddTaskDbHelper;

import org.w3c.dom.Text;

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
        long taskTime = -1;

        if (bundle != null) {
            taskTime = bundle.getLong("task_time");
        }

        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getTask(taskTime);

        TextView taskNameTv = (TextView) myView.findViewById(R.id.detail_item_name_tv);
        TextView taskStartDateTv = (TextView) myView.findViewById(R.id.detail_item_start_date_tv);
        TextView taskStartTimeTv = (TextView) myView.findViewById(R.id.detail_item_start_time_tv);
        TextView taskEndDateTv = (TextView) myView.findViewById(R.id.detail_item_end_date_tv);
        TextView taskEndTimeTv = (TextView) myView.findViewById(R.id.detail_item_end_time_tv);
        TextView taskLocationTv = (TextView) myView.findViewById(R.id.detail_item_location_tv);
        TextView taskReminderTv = (TextView) myView.findViewById(R.id.detail_item_reminder_tv);
        TextView taskTypeTv = (TextView) myView.findViewById(R.id.detail_item_type_tv);

        String nameFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        String locationFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_LOCATION));
        String typeFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TYPE));

        long startTimeFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_START));
        long endTimeFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_END));

        String[] start = helperFunctions.getDateTime(startTimeFromDb).split("\\s+");
        String[] end = helperFunctions.getDateTime(endTimeFromDb).split("\\s+");

        taskNameTv.setText(nameFromDb);
        taskStartDateTv.setText(start[0] + " " + start[1] + " " + start[2]);
        taskStartTimeTv.setText(start[3]);
        taskEndDateTv.setText(end[0] + " " + end[1] + " " + end[2]);
        taskEndTimeTv.setText(end[3]);
        taskLocationTv.setText(locationFromDb);
        taskTypeTv.setText(typeFromDb);

        String reminderFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_REMINDER));

        if (reminderFromDb.equals("Custom")) {
            long reminderCustomFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_CUSTOM_REMINDER));
            taskReminderTv.setText(helperFunctions.getDateTime(reminderCustomFromDb));
        }




        ImageView editButton= (ImageView) myView.findViewById(R.id.detail_item_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperFunctions.switchMainContentFragment(new AddTaskFragment(), getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
            }
        });


        return myView;
    }

}
