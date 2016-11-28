package com.lebartodev.mercurylab6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.lebartodev.mercurylab6.util.SharedPrefer;

/**
 * Created by Александр on 28.11.2016.
 */

public class TimeNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TimeNotification", "Receive");
        Intent notificationIntent = ShedulerActivity_.intent(context).get();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.rounded_button)
                .setTicker(SharedPrefer.getTaskModel().getTitle())
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                .setContentTitle(SharedPrefer.getTaskModel().getTitle()).setContentText(SharedPrefer.getTaskModel().getDescription());
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(2002, notification);
        wakeUpScreen(context);

    }
    private void wakeUpScreen(Context context){
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);

        boolean isScreenOn = pm.isScreenOn();

        if(isScreenOn==false)
        {

            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");

            wl.acquire(10000);
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

            wl_cpu.acquire(10000);
        }
    }
}