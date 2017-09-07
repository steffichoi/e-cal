package com.example.android.me_cal.Helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.me_cal.R;

import java.util.Calendar;

/**
 * Created by steffichoi on 9/1/17.
 */

public class HelperFunctionsFragment extends HelperFunctions{

    Activity activity;
    Fragment hFragment;

    public HelperFunctionsFragment(Context context, Fragment fragment) {
        super(context);
        activity = (Activity)context;
        hFragment = fragment;
    }

    public String[] getMonthDate() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        String dateFromCal = Integer.toString(day);
        String monthFromCal = new DateFormatSymbols().getMonths()[month];

        Bundle bundle = hFragment.getArguments();
        if (bundle != null) {
            dateFromCal = bundle.getString("date_from_cal", "no date");
            monthFromCal = bundle.getString("month_from_cal", "no month");
        }
        String[] monthDate = {monthFromCal, dateFromCal};
        return monthDate;
    }

    public void monthDateToTextView (View view, int monthId, int dateId) {

        TextView today_month_label_tv;
        TextView today_date_label_tv;

        String[] monthDate = getMonthDate();

        String monthFromCal = monthDate[0];
        String dateFromCal = monthDate[1];

        Bundle bundle = hFragment.getArguments();
        if (bundle != null) {
            dateFromCal = bundle.getString("date_from_cal", "no date");
            monthFromCal = bundle.getString("month_from_cal", "no month");

        }
        today_month_label_tv = (TextView) view.findViewById(monthId);
        today_date_label_tv = (TextView) view.findViewById(dateId);

        today_date_label_tv.setText(dateFromCal);
        today_month_label_tv.setText(monthFromCal);
    }
}