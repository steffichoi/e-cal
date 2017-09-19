package com.example.android.me_cal.NotificationUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by steffichoi on 9/18/17.
 */

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmId = intent.getIntExtra("alarm_id", -1);
        long startTime = intent.getLongExtra("start_time", -1);
        String name = intent.getStringExtra("alarm_name");
        EventNotificationUtil.eventReminder(context, alarmId, startTime, name);
    }
}
