package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        helperFunctions.monthDateToTextView (myView, R.id.today_month_tv, R.id.today_date_tv);

        //RECYCLER VIEW STUFF
        RecyclerView schedulerRecyclerView;

        schedulerRecyclerView = (RecyclerView) myView.findViewById(R.id.today_recycler_view);
        schedulerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER STUFF
        final AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();

        int[] todayDate = helperFunctions.getDate();
        final String dateQuery = Integer.toString(todayDate[0]) + "/" +
                Integer.toString(todayDate[1]+1) + "/" + Integer.toString(todayDate[2]);

        Cursor cursor = dbHelper.getDay(dateQuery);

        String[] timelist = {"10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00"};
        mAdapter = new TodayAdapter(getActivity(), cursor, timelist);

        schedulerRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                dbHelper.removeGuest(id);
                mAdapter.swapCursor(dbHelper.getDay(dateQuery));
            }
        }).attachToRecyclerView(schedulerRecyclerView);

        return myView;
    }
}
