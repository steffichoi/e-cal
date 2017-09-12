package com.example.android.me_cal.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by steffichoi on 8/17/17.
 */

public class CustomReminderFragment extends Fragment implements View.OnClickListener {

    View myView;
    TextView mReminderDateTv;
    TextView mReminderTimeTv;
    ImageView mSaveImg;
    ImageView mCancelImg;

    private DatePickerDialog.OnDateSetListener mReminderDateSetListener;
    private TimePickerDialog.OnTimeSetListener mReminderTimeSetListener;

    HelperFunctions helperFunctions = new HelperFunctions(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.custom_reminder_layout, container, false);
        final String startDate;
        final String startTime;

        final Calendar calendar = Calendar.getInstance();

        mReminderTimeTv = (TextView) myView.findViewById(R.id.custom_reminder_time_text_view);
        mReminderDateTv = (TextView) myView.findViewById(R.id.custom_reminder_date_text_view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            startDate = bundle.getString("start_date");
            startTime = bundle.getString("start_time");

            mReminderDateTv.setText(startDate);
            mReminderTimeTv.setText(startTime);
        } else {
            Toast.makeText(getActivity(), "Bundle Pass Error! Try again!", Toast.LENGTH_LONG).show();
            helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
            return myView;
        }

        mReminderDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dateArrayStart = startDate.split("\\s+");

                int year = Integer.parseInt(dateArrayStart[3]);
                int day = Integer.parseInt(dateArrayStart[1]);
                Date d;

                try {
                    d = new SimpleDateFormat("MMMM").parse(dateArrayStart[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int month = cal.get(Calendar.MONTH);

                SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
                long startMillis;
                try {
                    d = f.parse(dateArrayStart[1] + " " + dateArrayStart[2] + " " + dateArrayStart[3]);
                    startMillis = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        mReminderDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.WHITE));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(startMillis);
                datePickerDialog.show();
            }
        });
        mReminderDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString = new DateFormatSymbols().getMonths()[month];

                String dayOfWeek = DateFormat.format("EE", new Date(year, month, dayOfMonth - 1)).toString();
                mReminderDateTv.setText(dayOfWeek + ", " + dayOfMonth + " " + monthString + " " + year);
            }
        };

        mReminderTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] time = startTime.split(":");
                int hour = Integer.parseInt(time[0]);
                int min = Integer.parseInt(time[1]);

                TimePickerDialog timeStartPickerDialogue = new TimePickerDialog(
                        getActivity(),
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        mReminderTimeSetListener, hour, min, true);
                timeStartPickerDialogue.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                timeStartPickerDialogue.show();
            }
        });
        mReminderTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    String hourString;
                    String minString;
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        int hourCurr = calendar.get(Calendar.HOUR);
                        int minCurr = calendar.get(Calendar.MINUTE);
                        int yearCurr = calendar.get(Calendar.YEAR);
                        int monthCurr = calendar.get(Calendar.MONTH);
                        int dayCurr = calendar.get(Calendar.DAY_OF_MONTH);

                        minString = Integer.toString(minute);
                        hourString = Integer.toString(hourOfDay);
                        if (minute < 10) {
                            minString = "0" + minString;
                        }
                        if (hourOfDay < 10) {
                            hourString = "0" + hourString;
                        }

                        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm");
                        long startMillis, setMillis;
                        mReminderDateTv = (TextView) myView.findViewById(R.id.custom_reminder_date_text_view);
                        String[] setDate = mReminderDateTv.getText().toString().split("\\s+");
                        String[] start = startDate.split("\\s+");

                        try {
                            Date d = f.parse(start[1] + " " + start[2] + " " + start[3] + " " + startTime);
                            startMillis = d.getTime();

                            d = f.parse(setDate[1] + " " + setDate[2] + " " + setDate[3] + " " + hourString + ":" + minString);
                            setMillis = d.getTime();
                        } catch (ParseException e) {
                            Toast.makeText(getActivity(), "parse...", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            return;
                        }

                        if (setMillis > startMillis) {
                            Toast.makeText(getActivity(), "REMINDER CANNOT BE AFTER START TIME", Toast.LENGTH_LONG).show();
                            return;
                        } else if (setMillis < calendar.getTimeInMillis()) {
                            Toast.makeText(getActivity(), "REMINDER CANNOT BE IN THE PAST", Toast.LENGTH_LONG).show();
                            return;
                        }
                        mReminderTimeTv.setText(hourString + ":" + minString);
                    }
                };

        mSaveImg = (ImageView) myView.findViewById(R.id.save_button);
        mCancelImg = (ImageView) myView.findViewById(R.id.cancel_button);

        mSaveImg.setOnClickListener(this);
        mCancelImg.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.save_button:
                mReminderDateTv = (TextView) myView.findViewById(R.id.custom_reminder_date_text_view);
                mReminderTimeTv = (TextView) myView.findViewById(R.id.custom_reminder_time_text_view);
                String date = mReminderDateTv.getText().toString();
                String time = mReminderTimeTv.getText().toString();
                String reminder = date + " " + time;

                Fragment addTaskFragment = new AddTaskFragment();
                Bundle bundle = this.getArguments();
                Bundle newBundle = new Bundle();
                if (bundle != null) {
                    newBundle.putBundle("add_task_bundle", bundle);
                }
                newBundle.putString("custom_reminder", reminder);

                addTaskFragment.setArguments(newBundle);

                helperFunctions.switchMainContentFragment(addTaskFragment, getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());

                break;

            case R.id.cancel_button:
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
                break;
        }
    }
}
