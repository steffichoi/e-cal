package com.example.android.me_cal.NotificationUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.android.me_cal.MainActivity;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 9/16/17.
 */

public class EventNotificationUtil {
    private static final int EVENT_NOTIFICATION_PENDING_INTENT_ID = 1234;

    public static void eventReminder(Context context, int alarmId, long startTime, String name) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_access_alarm_blue_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("Event Reminder")
                .setSubText(name + " coming up!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(name + " coming up!"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context, alarmId))
                .setWhen(startTime)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(alarmId, notificationBuilder.build());
        Toast.makeText(context, "nofication reminder set!", Toast.LENGTH_SHORT).show();
    }

    private static PendingIntent contentIntent(Context context, int alarmId) {

        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, alarmId,
                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_access_alarm_blue_24dp);
    }
}
