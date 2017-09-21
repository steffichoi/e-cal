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
import android.view.Window;
import android.view.WindowManager;
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
    long startLong;

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
        Cursor cursor = dbHelper.getTask(taskTime);

        getViews();

        String nameFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        String locationFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_LOCATION));
        String typeFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TYPE));

        long startTimeFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_START));
        long endTimeFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_END));

        startLong = startTimeFromDb;
        String[] start = helperFunctions.getDateTime(startTimeFromDb).split("\\s+");
        String[] end = helperFunctions.getDateTime(endTimeFromDb).split("\\s+");

        taskNameTv.setText(nameFromDb);
        taskStartDateTv.setText(start[0] + " " + start[1] + " " + start[2] + " " + start[3]);
        taskStartTimeTv.setText(start[4]);
        taskEndDateTv.setText(end[0] + " " + end[1] + " " + end[2] + " " + end[3]);
        taskEndTimeTv.setText(end[4]);
        taskLocationTv.setText(locationFromDb);
        taskTypeTv.setText(typeFromDb);

        String reminderFromDb = cursor.getString(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_REMINDER));

        if (reminderFromDb.equals("Set Reminder")) {
            long reminderCustomFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_CUSTOM_REMINDER));
            taskReminderTv.setText(helperFunctions.getDateTime(reminderCustomFromDb));
        } else {
            taskReminderTv.setText("no reminder set");
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
                getViews();
                Fragment editFragment = new AddTaskFragment();
                Bundle bundle = new Bundle();

                String name = taskNameTv.getText().toString();
                String startDate = taskStartDateTv.getText().toString();
                String startTime = taskStartTimeTv.getText().toString();
                String endDate = taskEndDateTv.getText().toString();
                String endTime = taskEndTimeTv.getText().toString();
                String location = taskLocationTv.getText().toString();
                String type = taskTypeTv.getText().toString();
                String reminder = taskReminderTv.getText().toString();

                bundle.putString("event_name", name);
                bundle.putString("start_date", startDate);
                bundle.putString("start_time", startTime);
                bundle.putString("end_date", endDate);
                bundle.putString("end_time", endTime);
                bundle.putString("location", location);
                bundle.putString("type", type);
                bundle.putString("custom_reminder", reminder);
                bundle.putBoolean("edit_task", true);
                bundle.putLong("task_id", id);

                editFragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(editFragment, getActivity());

                Fragment sbFragment = new TodaySideBarFragment();

                String[] dateString = helperFunctions.getDateTime(startLong).split("\\s+");
                bundle.putInt("date_from_cal", Integer.parseInt(dateString[1]));
                bundle.putInt("month_from_cal", helperFunctions.getMonthInt(dateString[2]));
                bundle.putInt("year_from_cal", Integer.parseInt(dateString[3]));

                sbFragment.setArguments(bundle);
                helperFunctions.switchSideContentFragment(sbFragment, getActivity());
                break;

            case R.id.detail_item_delete_button:
                final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.confirm_delete_dialog_layout);

                Button yesButton = (Button) dialog.findViewById(R.id.confirm_dialog_yes_button);
                Button noButton = (Button) dialog.findViewById(R.id.confirm_dialog_no_button);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());

                        dbHelper.removeGuest(id);
                        Toast.makeText(getActivity(), "Deleted item!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Fragment tFragment = new TodayFragment();
                        Bundle b = new Bundle();
                        String[] dateString = helperFunctions.getDateTime(startLong).split("\\s+");
                        b.putInt("date_from_cal", Integer.parseInt(dateString[1]));
                        b.putInt("month_from_cal", helperFunctions.getMonthInt(dateString[2]));
                        b.putInt("year_from_cal", Integer.parseInt(dateString[3]));
                        tFragment.setArguments(b);

                        helperFunctions.switchMainContentFragment(tFragment, getActivity());
                        helperFunctions.switchSideContentFragment(new ToDoFragment(), getActivity());
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
