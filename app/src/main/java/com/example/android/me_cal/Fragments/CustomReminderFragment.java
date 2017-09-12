package com.example.android.me_cal.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/17/17.
 */

public class CustomReminderFragment extends Fragment implements View.OnClickListener {

    View myView;
    EditText mReminderMinEt;
    EditText mReminderHourEt;
    EditText mReminderDayEt;
    ImageView mSaveImg;
    ImageView mCancelImg;

    HelperFunctions helperFunctions = new HelperFunctions(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.custom_reminder_layout, container, false);

        mSaveImg = (ImageView) myView.findViewById(R.id.save_button);
        mCancelImg = (ImageView) myView.findViewById(R.id.cancel_button);

        mSaveImg.setOnClickListener(this);
        mCancelImg.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        mReminderMinEt = (EditText) myView.findViewById(R.id.custom_reminder_min_edit_text);
        mReminderHourEt = (EditText) myView.findViewById(R.id.custom_reminder_hour_edit_text);
        mReminderDayEt = (EditText) myView.findViewById(R.id.custom_reminder_days_edit_text);

        switch (v.getId()) {

            case R.id.save_button:
                String min = mReminderMinEt.getText().toString();
                String hour = mReminderHourEt.getText().toString();
                String day = mReminderDayEt.getText().toString();
                String reminder = day + " days " + hour + " hours " + min + " mins before event";

                Fragment addTaskFragment = new AddTaskFragment();
                Bundle bundle = this.getArguments();
                Bundle newBundle = new Bundle();
                if (bundle != null) {
                    newBundle.putBundle("add_task_bundle", bundle);
                }
                newBundle.putString("custom_reminder", reminder);

                addTaskFragment.setArguments(newBundle);

                helperFunctions.switchMainContentFragment(addTaskFragment, getActivity());
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());

                break;

            case R.id.cancel_button:
                helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), getActivity());
                break;
        }
    }
}
