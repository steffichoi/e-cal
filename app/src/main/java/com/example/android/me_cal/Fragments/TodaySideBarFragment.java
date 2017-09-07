package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.data.AddTaskDbHelper;
import com.example.android.me_cal.Adapters.ScheduleAdapter;
import com.example.android.me_cal.R;

import java.util.Calendar;

/**
 * Created by steffichoi on 8/17/17.
 */

public class TodaySideBarFragment extends Fragment {

    View myView;

    private ScheduleAdapter mAdapter;
    private SQLiteDatabase mDb;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.today_side_bar_layout, container, false);

        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        TextView today_month_label_tv;
        TextView today_date_label_tv;
        String dateFromCal = Integer.toString(day);
        String monthFromCal = new DateFormatSymbols().getMonths()[month];;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dateFromCal = bundle.getString("date_from_cal", "no date");
            monthFromCal = bundle.getString("month_from_cal", "no month");

        }
        today_month_label_tv = (TextView) myView.findViewById(R.id.today_month_header_tv);
        today_date_label_tv = (TextView) myView.findViewById(R.id.today_date_header_tv);

        today_date_label_tv.setText(dateFromCal);
        today_month_label_tv.setText(monthFromCal);

        //RECYCLER VIEW STUFF
        RecyclerView schedulerRecyclerView;

        schedulerRecyclerView = (RecyclerView) myView.findViewById(R.id.today_recycler_view);
        schedulerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //ADAPTER STUFF
        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getAllTasks();

        mAdapter = new ScheduleAdapter(getActivity(), cursor);

        schedulerRecyclerView.setAdapter(mAdapter);

        //         FLOATING ACTION BUTTON TO TODAY SCHEDULE
        FloatingActionButton dateFab = (FloatingActionButton) myView.findViewById(R.id.date_fab);
        String[] monthDate = helperFunctions.getMonthDate();

        final String finalDateFromCal = monthDate[0];
        final String finalMonthFromCal = monthDate[1];

        dateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new TodayFragment();
                Bundle bundle = new Bundle();

                bundle.putString("date_from_cal", finalDateFromCal);
                bundle.putString("month_from_cal", finalMonthFromCal);
                fragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(fragment, getActivity());
                helperFunctions.switchSideContentFragment(new ToDoFragment(), getActivity());

            }
        });


        return myView;
    }
}
