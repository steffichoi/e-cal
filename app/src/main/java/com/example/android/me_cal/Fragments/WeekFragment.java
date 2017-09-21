package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.me_cal.Adapters.TodaySideBarAdapter;
import com.example.android.me_cal.Adapters.WeekAdapter;
import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskDbHelper;

/**
 * Created by steffichoi on 8/17/17.
 */

public class WeekFragment extends Fragment {

    View myView;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.week_day_layout, container, false);

        helperFunctions.dayDateToTextView(myView, R.id.week_day_tv, R.id.week_date_tv);

        RecyclerView schedulerRecyclerView;

        schedulerRecyclerView = (RecyclerView) myView.findViewById(R.id.week_day_recycler_view);
        schedulerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER STUFF
        WeekAdapter mAdapter;
        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());

        int[] todayDate = helperFunctions.getDateIntArray();
        final String dateQuery = Integer.toString(todayDate[0]) + " " +
                new DateFormatSymbols().getMonths()[todayDate[1]] + " " + Integer.toString(todayDate[2]);
        long longDate = helperFunctions.getLongDate(dateQuery);

        Cursor cursor = dbHelper.getDay(longDate);
        mAdapter = new WeekAdapter(getActivity(), cursor);

        schedulerRecyclerView.setAdapter(mAdapter);

        return myView;
    }
}
