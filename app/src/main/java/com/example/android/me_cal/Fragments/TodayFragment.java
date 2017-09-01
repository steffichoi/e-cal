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

import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.data.AddTaskDbHelper;
import com.example.android.me_cal.data.TestUtil;
import com.example.android.me_cal.Adapters.ScheduleAdapter;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/17/17.
 */

public class TodayFragment extends Fragment {

    View myView;

    private ScheduleAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.today_layout, container, false);

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
        return myView;
    }
}
