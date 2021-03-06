package com.example.android.me_cal.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.me_cal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void switchFragment(int frame_id, Fragment fragment, Context context) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(frame_id, fragment);

        fragmentTransaction.commit();

    }

    public int getMonthInt(String monthString) {
        Date date;

        try {
            date = new SimpleDateFormat("MMMM").parse(monthString);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public long getParsedDateInMillis(String day, String month, String year) {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
        Date date;
        try {
            date = f.parse(day + " " + month + " " + year);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long[] getParsedDateTimeInMillis(String date1, String date2, String time1, String time2)
    {
        String[] dateStart = date1.split("\\s+");
        String[] dateEnd = date2.split("\\s+");

        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm");
        long[] millis = new long[2];
        try {
            Date d1 = f.parse(dateStart[1] + " " + dateStart[2] + " " + dateStart[3] + " " + time1);
            Date d2 = f.parse(dateEnd[1] + " " + dateEnd[2] + " " + dateEnd[3] + " " + time2);
            millis[0] = d1.getTime();
            millis[1] = d2.getTime();
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
            return millis;
        }
    }

    public void setTimePickerDialog(TimePickerDialog.OnTimeSetListener listener,
                                    int hour, int min, Activity activity) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                activity, R.style.Theme_AppCompat_DayNight_Dialog, listener, hour, min, true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }

    public void setDatePickerDialog(
            DatePickerDialog.OnDateSetListener listener, int year, int month, int day,
            long minDate, long maxDate, Activity activity) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity, R.style.Theme_AppCompat_DayNight_Dialog, listener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        datePickerDialog.getDatePicker().setMinDate(minDate);

        if (maxDate != -1) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
        }
        datePickerDialog.show();
    }

    public String getDateTime(long millis) {
        SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return f.format(calendar.getTime());
    }

    public String getTime(long millis) {
        android.icu.text.SimpleDateFormat f = new android.icu.text.SimpleDateFormat("dd MMM yyyy HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return f.format(calendar.getTime()).split("\\s+")[3];

    }

    public long getLongDate(String date) {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date d = f.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void openLocationInMap(String address, Context context) {

        Toast.makeText(context, address, Toast.LENGTH_LONG).show();
        Uri geoLocation = Uri.parse("geo:0,0?q=" + address);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!",
                    Toast.LENGTH_LONG);
        }
    }

    public int[] getCurrDateIntArray() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int year = now.get(Calendar.YEAR);

        int[] monthDate = {day, month, year};
        return monthDate;
    }
}
