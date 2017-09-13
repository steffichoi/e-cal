package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.data.AddTaskDbHelper;
import com.example.android.me_cal.Adapters.TodaySideBarAdapter;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/17/17.
 */

public class TodaySideBarFragment extends Fragment {

    View myView;

    private TodaySideBarAdapter mAdapter;
    private SQLiteDatabase mDb;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.today_side_bar_layout, container, false);

        helperFunctions.monthDateToTextView(
                myView, R.id.today_month_header_tv, R.id.today_date_header_tv);

        //RECYCLER VIEW STUFF
        RecyclerView schedulerRecyclerView;

        schedulerRecyclerView = (RecyclerView) myView.findViewById(R.id.today_recycler_view);
        schedulerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //ADAPTER STUFF
        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        int[] todayDate = helperFunctions.getDateIntArray();
        String dateQuery = Integer.toString(todayDate[0]) + "/" +
                Integer.toString(todayDate[1]+1) + "/" + Integer.toString(todayDate[2]);
        long longDate = helperFunctions.getLongDate(dateQuery);

        Cursor cursor = dbHelper.getDay(longDate);

        mAdapter = new TodaySideBarAdapter(getActivity(), cursor);

        schedulerRecyclerView.setAdapter(mAdapter);

        //         FLOATING ACTION BUTTON TO TODAY SCHEDULE
        FloatingActionButton dateFab = (FloatingActionButton) myView.findViewById(R.id.date_fab);
        final int[] monthDate = helperFunctions.getDateIntArray();

        dateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new TodayFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("date_from_cal", monthDate[0]);
                bundle.putInt("month_from_cal", monthDate[1]);
                bundle.putInt("year_from_cal", monthDate[2]);
                fragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(fragment, getActivity());
                helperFunctions.switchSideContentFragment(new ToDoFragment(), getActivity());
            }
        });
        return myView;
    }
}
