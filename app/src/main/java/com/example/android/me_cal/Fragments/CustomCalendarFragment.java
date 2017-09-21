package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.android.me_cal.R;
import com.example.android.me_cal.Helper.HelperFunctions;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by steffichoi on 8/17/17.
 */

public class CustomCalendarFragment extends Fragment {

    View myView;
    CalendarView calendarView;
    HelperFunctions helperFunctions = new HelperFunctions(getActivity());

    private int[] date = new int[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.custom_calendar_layout, container, false);
        calendarView = (CalendarView) myView.findViewById(R.id.calendarView);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        String[] dateString = sdf.format(new Date(calendarView.getDate())).split("\\s+");
        date[0] = Integer.parseInt(dateString[0]);
        date[1] = Integer.parseInt(dateString[1])-1;
        date[2] = Integer.parseInt(dateString[2]);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Fragment newSideBarFragment = new TodaySideBarFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("date_from_cal", dayOfMonth);
                bundle.putInt("month_from_cal", month);
                bundle.putInt("year_from_cal", year);

                date[0] = dayOfMonth;
                date[1] = month;
                date[2] = year;

                newSideBarFragment.setArguments(bundle);

                helperFunctions.switchSideContentFragment(newSideBarFragment, getActivity());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperFunctions.switchMainContentFragment(new AddTaskFragment(), getActivity());
            }
        });

        return myView;
    }

    public int[] getDatePicked() {
        return date;
    }

}
