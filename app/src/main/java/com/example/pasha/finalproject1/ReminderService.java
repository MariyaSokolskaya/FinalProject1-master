package com.example.pasha.finalproject1;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by sma on 20.02.2018.
 */

public class ReminderService extends IntentService {
    private static final  String TAG = "ReminderService";

    public ReminderService(){
        super(TAG);
    }
    public ReminderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent clickNotifIntent = new Intent(this, MainActivity.class);
        PendingIntent pClickNotifIntent = PendingIntent.getActivity(this, 0, clickNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("Title").
                setContentText("Content Text").
                setContentIntent(pClickNotifIntent);
        Notification notification = notifBuilder.build();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
        //новое уведомление на месте старого - главное сохранить значение первого
        //аргумента в методе notify, т.е 1 - это признак, по которому система ищет
        // существующее уведомление. Другой аргумент => новое уведомление
    }
    public static void setServiceAlarm(Context context, boolean onOffService){
        Intent intent = new Intent(context, ReminderService.class);
        PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if(onOffService){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 15000, pIntent);

        }else {
            alarmManager.cancel(pIntent);
            pIntent.cancel();
        }
    }

}
