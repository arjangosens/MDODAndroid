package com.project.avans.mdodandroid.applicationLogic.notifications;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.MyPersonalRiskActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService {

    private static String channelId;




    public static void Notificat(Notification notification, int delay, Context context) {


        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public static Notification getNotification(String content, Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            channel(context);
            Notification.Builder builder = new Notification.Builder(context, channelId);
            builder.setContentTitle("Tactus Bericht");
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.tactuslogo_small_round);
            return builder.build();
        }
        else{
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("Tactus Bericht");
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.tactuslogo_small_round);
            return builder.build();
        }
    }
    private static void channel(Context context) {

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            channelId = "Tactus channel Id";
            CharSequence channelName = "Tactus";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);

        }

        }
    }



