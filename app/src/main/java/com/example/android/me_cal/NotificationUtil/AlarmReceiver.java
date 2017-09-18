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
        EventNotificationUtil.eventReminder(context);
    }
}
