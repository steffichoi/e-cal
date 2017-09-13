package com.example.android.me_cal.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormatSymbols;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskDbHelper;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by steffichoi on 8/18/17.
 */

public class AddTaskFragment extends Fragment implements View.OnClickListener {

    View myView;

    private EditText mEventNameEditText;
    private TextView mEventDateStartTextView;
    private TextView mEventDateEndTextView;
    private TextView mEventTimeStartTextView;
    private TextView mEventTimeEndTextView;
    private EditText mEventLocationEditText;
    private ImageView mEventLocationImageView;

    private Spinner mEventTypeSpinner;
    private Spinner mEventReminderSpinner;
    private TextView mEventCustomReminderTextView;

    String[] eventTypes = new String[] {"Event", "Task", "Schedule"};
    String[] eventReminders = new String[] {"No Reminder", "30   mins", "60   mins", "Custom"};

    private DatePickerDialog.OnDateSetListener mDateStartSetListener;
    private DatePickerDialog.OnDateSetListener mDateEndSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeStartSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeEndSetListener;

    private FloatingActionButton mSaveFab;
    private FloatingActionButton mCancelFab;

    HelperFunctions helperFunctions = new HelperFunctions(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.add_task_layout, container, false);

        getViews();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Bundle transferBundle = bundle.getBundle("add_task_bundle");

            if (transferBundle != null) {
                String name = transferBundle.getString("event_name");
                String startDate = transferBundle.getString("start_date");
                String startTime = transferBundle.getString("start_time");
                String endDate = transferBundle.getString("end_date");
                String endTime = transferBundle.getString("end_time");
                String location = transferBundle.getString("location");
                String type = transferBundle.getString("type");
                String reminder = transferBundle.getString("reminder");

                mEventNameEditText.setText(name);
                mEventDateStartTextView.setText(startDate);
                mEventTimeStartTextView.setText(startTime);
                mEventDateEndTextView.setText(endDate);
                mEventTimeEndTextView.setText(endTime);
                mEventLocationEditText.setText(location);

                ArrayAdapter<String> typeAdapter = setSpinnerAdapter (eventTypes, mEventTypeSpinner);
                int typePos = typeAdapter.getPosition(type);
                mEventTypeSpinner.setSelection(typePos);

                ArrayAdapter<String> reminderAdapter = setSpinnerAdapter(eventReminders, mEventReminderSpinner);
                int reminderPos = reminderAdapter.getPosition(reminder);
                mEventReminderSpinner.setSelection(reminderPos);
            }
            String customReminder = bundle.getString("custom_reminder");
            mEventCustomReminderTextView.setText(customReminder);

        } else {
            ArrayAdapter<String> typeAdapter = setSpinnerAdapter (eventTypes, mEventTypeSpinner);
            ArrayAdapter<String> reminderAdapter = setSpinnerAdapter(eventReminders, mEventReminderSpinner);

            mEventReminderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String reminder_choice = mEventReminderSpinner.getSelectedItem().toString();
                    if (reminder_choice.equals("Custom")){
                        if (eventNotSet()) {
                            Toast.makeText(getActivity(),
                                    "PICK EVENT START AND END TIMES \n" +
                                            "BEFORE SETTING CUSTOM REMINDER", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Fragment customFragment = new CustomReminderFragment();
                        Bundle bundle = new Bundle();

                        String name = mEventNameEditText.getText().toString();
                        String startDate = mEventDateStartTextView.getText().toString();
                        String startTime = mEventTimeStartTextView.getText().toString();
                        String endDate = mEventDateEndTextView.getText().toString();
                        String endTime = mEventTimeEndTextView.getText().toString();
                        String location = mEventLocationEditText.getText().toString();
                        String type = mEventTypeSpinner.getSelectedItem().toString();
                        String reminder = mEventReminderSpinner.getSelectedItem().toString();

                        bundle.putString("event_name", name);
                        bundle.putString("start_date", startDate);
                        bundle.putString("start_time", startTime);
                        bundle.putString("end_date", endDate);
                        bundle.putString("end_time", endTime);
                        bundle.putString("location", location);
                        bundle.putString("type", type);
                        bundle.putString("reminder", reminder);

                        customFragment.setArguments(bundle);

                        helperFunctions.switchSideContentFragment(customFragment, getActivity());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        mEventDateStartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                helperFunctions.setDatePickerDialog(
                        mDateStartSetListener, year, month,day,
                        calendar.getTimeInMillis(), -1, getActivity());
            }
        });
        mDateStartSetListener = setDateListener(mEventDateStartTextView, mEventDateEndTextView);

        mEventDateEndTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventDateStartTextView.getText().toString().equals("Select Start Date")) {
                    Toast.makeText(getActivity(), "PICK START DATE BEFORE END DATE!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    String[] dateString = mEventDateStartTextView.getText().toString().split("\\s+");

                    int year = Integer.parseInt(dateString[3]);
                    int day = Integer.parseInt(dateString[1]);
                    int month = helperFunctions.getMonthInt(dateString[2]);

                    long minDate = helperFunctions.getParsedDateInMillis(
                            dateString[1], dateString[2], dateString[3]);

                    helperFunctions.setDatePickerDialog(
                            mDateEndSetListener, year, month,day, minDate, -1, getActivity());
                }
            }
        });
        mDateEndSetListener = setDateListener(mEventDateEndTextView, mEventDateEndTextView);

        mEventTimeStartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int min = calendar.get(Calendar.MINUTE);

                helperFunctions.setTimePickerDialog(mTimeStartSetListener, hour, min, getActivity());
            }
        });
        mTimeStartSetListener = setTimeListener(mEventTimeStartTextView, mEventTimeEndTextView);

        mEventTimeEndTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mEventDateEndTextView.getText().toString().equals("Select End Date")) {
                            Toast.makeText(getActivity(), "SELECT END DATE BEFORE TIME!",
                                    Toast.LENGTH_LONG).show();

                        } else if (mEventTimeStartTextView.getText().toString().equals("Select Event Start Time")) {
                            Toast.makeText(getActivity(), "SELECT START TIME BEFORE END TIME!",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Calendar calendar = Calendar.getInstance();
                            int hour = calendar.get(Calendar.HOUR);
                            int min = calendar.get(Calendar.MINUTE);

                            helperFunctions.setTimePickerDialog(mTimeEndSetListener, hour, min, getActivity());
                        }
                    }
                });
        mTimeEndSetListener = setTimeListener(mEventTimeEndTextView, mEventTimeEndTextView);

        mEventLocationImageView.setOnClickListener(this);
        mSaveFab.setOnClickListener(this);
        mCancelFab.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.save_fab:

                addToSchedule();

                helperFunctions.switchMainContentFragment(new TodayFragment(), getActivity());
                helperFunctions.switchSideContentFragment(new ToDoFragment(), getActivity());
                break;

            case R.id.cancel_fab:

                helperFunctions.switchMainContentFragment(new CustomCalendarView(), getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
                break;

            case R.id.event_location_image_view:
                mEventLocationEditText = (EditText) myView.findViewById(R.id.event_location_edit_text);
                String address = mEventLocationEditText.getText().toString();

                if (address.equals("")) {
                    Toast.makeText(getActivity(), "No address entered!", Toast.LENGTH_SHORT).show();
                } else {
                    openLocationInMap(address);
                }
                break;
        }
    }

    public void addToSchedule() {

        if (mEventNameEditText.getText().length() == 0 ||
                mEventDateStartTextView.getText().equals("Select Date") ||
                mEventTimeStartTextView.getText().equals("Select Event Start Time") ||
                mEventTimeEndTextView.getText().equals("Select Event End Time") ||
                mEventLocationEditText.getText().length() == 0) return;

        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());

        dbHelper.addEvent(mEventNameEditText.getText().toString(),
                mEventDateStartTextView.getText().toString(),
                mEventTimeEndTextView.getText().toString(),
                mEventTimeEndTextView.getText().toString(),
                mEventLocationEditText.getText().toString());
    }

    private DatePickerDialog.OnDateSetListener setDateListener(final TextView startTv, final TextView endTv) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString = new DateFormatSymbols().getMonths()[month];

                String dayOfWeek = DateFormat.format("EE", new Date(year, month, dayOfMonth - 1)).toString();
                startTv.setText(dayOfWeek + ", " + dayOfMonth + " " + monthString + " " + year);
                endTv.setText(dayOfWeek + ", " + dayOfMonth + " " + monthString + " " + year);
            }
        };
    }

    private TimePickerDialog.OnTimeSetListener setTimeListener(final TextView startTv, final TextView endTv) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minString = Integer.toString(minute);
                String hourString = Integer.toString(hourOfDay);

                if (minute < 10) {
                    minString = "0" + minString;
                }
                if (hourOfDay < 10) {
                    hourString = "0" + hourString;
                }
                String timeStart = hourString + ":" + minString;

                if (startTv == mEventTimeEndTextView) {
                    String timeEnd = hourString + ":" + minString;

                    long[] millis = helperFunctions.getParsedDateTimeInMillis(
                            mEventDateStartTextView.getText().toString(),
                            mEventDateEndTextView.getText().toString(),
                            mEventTimeStartTextView.getText().toString(),
                            timeEnd);

                    if (millis[1] < millis[0]) {
                        Toast.makeText(getActivity(), "END TIME CANNOT BE BEFORE START TIME!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                } else if (startTv == mEventTimeStartTextView) {
                    Calendar calendar = Calendar.getInstance();
                    int hourCurr = calendar.get(Calendar.HOUR);
                    int minCurr = calendar.get(Calendar.MINUTE);
                    int yearCurr = calendar.get(Calendar.YEAR);
                    int monthCurr = calendar.get(Calendar.MONTH);
                    int dayCurr = calendar.get(Calendar.DAY_OF_MONTH);

                    String dateCurr = "DAY, " + dayCurr + " " + new DateFormatSymbols().getMonths()[monthCurr] + " " + yearCurr;
                    String timeCurr = hourCurr + ":" + minCurr;

                    long[] millis = helperFunctions.getParsedDateTimeInMillis(
                            mEventDateStartTextView.getText().toString(),
                            dateCurr, timeStart, timeCurr);

                    if (millis[0] < millis[1]) {
                        Toast.makeText(getActivity(), "START TIME CANNOT BE IN THE PAST!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                startTv.setText(timeStart);
                endTv.setText(timeStart);
            }
        };
    }

    private void openLocationInMap(String address) {

        Toast.makeText(getActivity(), address, Toast.LENGTH_LONG).show();
        Uri geoLocation = Uri.parse("geo:0,0?q=" + address);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!",
                    Toast.LENGTH_LONG);
        }
    }

    private void getViews() {
        mEventNameEditText = (EditText) myView.findViewById(R.id.event_name_edit_text);
        mEventDateStartTextView = (TextView) myView.findViewById(R.id.event_date_start_text_view);
        mEventDateEndTextView = (TextView) myView.findViewById(R.id.event_date_end_text_view);
        mEventTimeStartTextView = (TextView) myView.findViewById(R.id.event_time_start_text_view);
        mEventTimeEndTextView = (TextView) myView.findViewById(R.id.event_time_end_text_view);
        mEventLocationEditText = (EditText) myView.findViewById(R.id.event_location_edit_text);
        mEventLocationImageView = (ImageView) myView.findViewById(R.id.event_location_image_view);
        mEventTypeSpinner = (Spinner) myView.findViewById(R.id.event_type_spinner);
        mEventReminderSpinner = (Spinner) myView.findViewById(R.id.set_reminder_spinner);
        mEventCustomReminderTextView = (TextView) myView.findViewById(R.id.custom_reminder_text_view);
        mSaveFab = (FloatingActionButton) myView.findViewById(R.id.save_fab);
        mCancelFab = (FloatingActionButton) myView.findViewById(R.id.cancel_fab);
    }

    private ArrayAdapter<String> setSpinnerAdapter (String[] spinnerVals, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_layout, spinnerVals);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout);
        spinner.setAdapter(adapter);
        return adapter;
    }

    private boolean eventNotSet() {
        if (mEventDateStartTextView.getText().equals("Select Start Date") ||
                mEventTimeStartTextView.getText().equals("Select Event Start Time") ||
                mEventDateEndTextView.getText().toString().equals("Select End Date") ||
            mEventTimeEndTextView.getText().equals("Select Event End Time")) {
            return true;
        }
        return false;
    }
}
