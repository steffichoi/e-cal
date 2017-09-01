package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/17/17.
 */

public class CustomCalendarView extends Fragment {

    View myView;
    CalendarView calendarView;
    Long date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.custom_calendar_layout, container, false);
        calendarView = (CalendarView) myView.findViewById(R.id.calendarView);
        date = calendarView.getDate();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_content_frame, new TodayFragment());

                fragmentTransaction.commit();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_content_frame, new AddTaskFragment());

                fragmentTransaction.commit();
            }
        });

        return myView;
    }
}
