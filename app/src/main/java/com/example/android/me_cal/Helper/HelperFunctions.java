package com.example.android.me_cal.Helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 9/1/17.
 */

public class HelperFunctions {

    Activity activity;
    public HelperFunctions(Context context) {
        activity = (Activity) context;
    }


    public void switchMainContentFragment(Fragment fragment, Context context) {
        Activity activity = (Activity) context;
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_content_frame, fragment);

        fragmentTransaction.commit();
    }

    public void switchSideContentFragment(Fragment fragment, Context context) {
        Activity activity = (Activity) context;
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.side_content_frame, fragment);

        fragmentTransaction.commit();
    }
}
