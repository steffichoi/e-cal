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

/**
 * Created by steffichoi on 8/17/17.
 */

public class CustomCalendarView extends Fragment {

    View myView;
    CalendarView calendarView;
    Long date;
    HelperFunctions helperFunctions = new HelperFunctions(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.custom_calendar_layout, container, false);
        calendarView = (CalendarView) myView.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Fragment newSideBarFragment = new TodaySideBarFragment();
                Bundle bundle = new Bundle();

                String monthFromCal = new DateFormatSymbols().getMonths()[month];;
                String dateFromCal = Integer.toString(dayOfMonth);

                bundle.putString("date_from_cal", dateFromCal);
                bundle.putString("month_from_cal", monthFromCal);
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
}
