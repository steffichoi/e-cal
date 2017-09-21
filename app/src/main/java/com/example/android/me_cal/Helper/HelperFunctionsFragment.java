package com.example.android.me_cal.Helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public int[] getDateIntArray() {
        int[] monthDate = getCurrDateIntArray();

        Bundle bundle = hFragment.getArguments();
        if (bundle != null) {
            monthDate[0] = bundle.getInt("date_from_cal", 01);
            monthDate[1] = bundle.getInt("month_from_cal", 01);
            monthDate[2] = bundle.getInt("year_from_cal", 2000);
        }
        return monthDate;
    }

    public void monthDateToTextView (View view, int monthId, int dateId) {

        TextView today_month_label_tv;
        TextView today_date_label_tv;

        int[] monthDate = getDateIntArray();

        String dateFromCal = Integer.toString(monthDate[0]);
        String monthFromCal = new DateFormatSymbols().getMonths()[monthDate[1]];

        Bundle bundle = hFragment.getArguments();
        if (bundle != null) {
            dateFromCal = Integer.toString(bundle.getInt("date_from_cal", 01));
            monthFromCal = new DateFormatSymbols().getMonths()[bundle.getInt("month_from_cal", 01)];

        }
        today_month_label_tv = (TextView) view.findViewById(monthId);
        today_date_label_tv = (TextView) view.findViewById(dateId);

        today_date_label_tv.setText(dateFromCal);
        today_month_label_tv.setText(monthFromCal);
    }

    public void dayDateToTextView (View view, int dayId, int dateId) {

        TextView week_day_label_tv = (TextView) view.findViewById(dayId);
        TextView week_date_label_tv = (TextView) view.findViewById(dateId);

        int[] monthDate = getDateIntArray();

        Bundle bundle = hFragment.getArguments();
        if (bundle != null) {
            monthDate[0] = bundle.getInt("date_from_cal", 01);
            monthDate[1] = bundle.getInt("month_from_cal", 01);
            monthDate[2] = bundle.getInt("year_from_cal", 2000);
        }

        String monthString = new DateFormatSymbols().getMonths()[monthDate[1]];
        long date = getLongDate(Integer.toString(monthDate[0]) + " " + monthString + " " + monthDate[2]);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MM yyyy");
        String[] dateString = sdf.format(date).split("\\s+");

        week_day_label_tv.setText(dateString[0]);
        week_date_label_tv.setText(dateString[1]);
    }
}
