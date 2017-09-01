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
import android.widget.EditText;

import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskDbHelper;

import java.util.Calendar;

/**
 * Created by steffichoi on 8/18/17.
 */

public class AddTaskFragment extends Fragment implements View.OnClickListener {

    View myView;

    private static final String LOG_TAG = "class.getSimpleName()";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        FloatingActionButton mSaveFab;
        FloatingActionButton mCancelFab;

        myView = inflater.inflate(R.layout.add_task_layout, container, false);

        mSaveFab = (FloatingActionButton) myView.findViewById(R.id.save_fab);
        mSaveFab.setOnClickListener(this);

        mCancelFab = (FloatingActionButton) myView.findViewById(R.id.cancel_fab);
        mCancelFab.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(v.getId()){

            case R.id.save_fab:

                addToSchedule();

                fragmentTransaction.replace(R.id.main_content_frame, new TodayFragment());
                fragmentTransaction.replace(R.id.side_content_frame, new ToDoFragment());
                break;

            case R.id.cancel_fab:

                fragmentTransaction.replace(R.id.main_content_frame, new CustomCalendarView());
                break;
        }
        fragmentTransaction.commit();

    }

    public void addToSchedule() {
        EditText mEventNameEditText = (EditText) myView.findViewById(R.id.event_name_edit_text);;
        EditText mEventDateEditText = (EditText) myView.findViewById(R.id.event_date_edit_text);;
        EditText mEventTimeEditText = (EditText) myView.findViewById(R.id.event_time_edit_text);;
        EditText mEventDurationEditText = (EditText) myView.findViewById(R.id.event_duration_edit_text);;
        EditText mEventLocationEditText = (EditText) myView.findViewById(R.id.event_location_edit_text);;

        if (mEventNameEditText.getText().length() == 0 ||
                mEventDateEditText.getText().length() == 0 ||
                mEventTimeEditText.getText().length() == 0 ||
                mEventLocationEditText.getText().length() == 0) return;

        AddTaskDbHelper dbHelper = new AddTaskDbHelper(getActivity());

        dbHelper.addEvent(mEventNameEditText.getText().toString(),
                mEventDateEditText.getText().toString(),
                mEventTimeEditText.getText().toString(),
                mEventDurationEditText.getText().toString(),
                mEventLocationEditText.getText().toString());



    }

}
