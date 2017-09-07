package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.me_cal.data.AddTaskDbHelper;
import com.example.android.me_cal.Adapters.TodayAdapter;
import com.example.android.me_cal.Helper.HelperFunctionsFragment;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/17/17.
 */

public class TodayFragment extends Fragment {

    View myView;


    private TodayAdapter mAdapter;
    private SQLiteDatabase mDb;

    HelperFunctionsFragment helperFunctions = new HelperFunctionsFragment(getActivity(), this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.today_layout, container, false);

        helperFunctions.monthDateToTextView (myView, R.id.today_date_tv, R.id.today_month_tv);

        //RECYCLER VIEW STUFF
        RecyclerView schedulerRecyclerView;

        schedulerRecyclerView = (RecyclerView) myView.findViewById(R.id.today_recycler_view);
        schedulerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER STUFF
        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getAllTasks();

        String[] timelist = {"10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00"};
        mAdapter = new TodayAdapter(getActivity(), cursor, timelist);

        schedulerRecyclerView.setAdapter(mAdapter);

        return myView;
    }
}
