package com.example.android.me_cal.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormatSymbols;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.data.AddTaskDbHelper;

import org.w3c.dom.Text;

/**
 * Created by steffichoi on 8/18/17.
 */

public class TaskDetailFragment extends Fragment implements View.OnClickListener{

    View myView;
    private SQLiteDatabase mDb;

    TextView taskNameTv;
    TextView taskStartDateTv;
    TextView taskStartTimeTv;
    TextView taskEndDateTv;
    TextView taskEndTimeTv;
    TextView taskLocationTv;
    TextView taskReminderTv;
    TextView taskTypeTv;
    ImageView taskLocationIv;
    ImageView editButton;
    ImageView deleteButton;

    long id = -1;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_detail_layout, container, false);

        Bundle bundle = getArguments();
        long taskTime = -1;

        if (bundle != null) {
            taskTime = bundle.getLong("task_time");
            id = bundle.getLong("task_id");
        }

        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getTask(taskTime);

        getViews();

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

        taskLocationIv.setOnClickListener(this);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.detail_item_edit_button:
                helperFunctions.switchMainContentFragment(new AddTaskFragment(), getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
                break;

            case R.id.detail_item_delete_button:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.confirm_delete_dialog_layout);

                Button yesButton = (Button) dialog.findViewById(R.id.confirm_dialog_yes_button);
                Button noButton = (Button) dialog.findViewById(R.id.confirm_dialog_no_button);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
                        dbHelper.getWritableDatabase();
                        dbHelper.removeGuest(id);
                        Toast.makeText(getActivity(), "Deleted item!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        helperFunctions.switchMainContentFragment(new TodayFragment(), getActivity());
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            case R.id.detail_item_location_iv:
                String address = taskLocationTv.getText().toString();
                if (address.equals("")) {
                    Toast.makeText(getActivity(), "This event has no address!", Toast.LENGTH_SHORT).show();
                } else {
                    helperFunctions.openLocationInMap(address, getActivity());
                }
                break;
        }
    }

    private void getViews() {
        taskNameTv = (TextView) myView.findViewById(R.id.detail_item_name_tv);
        taskStartDateTv = (TextView) myView.findViewById(R.id.detail_item_start_date_tv);
        taskStartTimeTv = (TextView) myView.findViewById(R.id.detail_item_start_time_tv);
        taskEndDateTv = (TextView) myView.findViewById(R.id.detail_item_end_date_tv);
        taskEndTimeTv = (TextView) myView.findViewById(R.id.detail_item_end_time_tv);
        taskLocationTv = (TextView) myView.findViewById(R.id.detail_item_location_tv);
        taskReminderTv = (TextView) myView.findViewById(R.id.detail_item_reminder_tv);
        taskTypeTv = (TextView) myView.findViewById(R.id.detail_item_type_tv);
        taskLocationIv = (ImageView) myView.findViewById(R.id.detail_item_location_iv);
        editButton= (ImageView) myView.findViewById(R.id.detail_item_edit_button);
        deleteButton = (ImageView) myView.findViewById(R.id.detail_item_delete_button);
    }
}
