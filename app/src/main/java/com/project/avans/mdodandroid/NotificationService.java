package com.project.avans.mdodandroid;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.nio.channels.Channel;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

        Timer timer;
        TimerTask timerTask;
        String TAG = "Timers";
        int Your_X_SECS = 5;


        @Override
        public IBinder onBind(Intent arg0)
        {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            Log.e(TAG, "onStartCommand");
            super.onStartCommand(intent, flags, startId);
            stoptimertask();
            startTimer();

            return START_STICKY;
        }


        @Override
        public void onCreate(){
            Log.e(TAG, "onCreate");


        }

        @Override
        public void onDestroy(){
            Log.e(TAG, "onDestroy");
            stoptimertask();
            super.onDestroy();


        }

        //we are going to use a handler to be able to run in our TimerTask
        final Handler handler = new Handler();


        public  void startTimer() {
            //set a new Timer
            timer = new Timer();

            //initialize the TimerTask's job
            initializeTimerTask();

            //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
            timer.schedule(timerTask, 2*24*60*60*1000); //
            //timer.schedule(timerTask, 5000,1000); //
        }

        public void stoptimertask() {
            //stop the timer, if it's not already null
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }

        public void initializeTimerTask() {

            timerTask = new TimerTask() {
                public void run() {

                    //use a handler to run a toast that shows the current timestamp
                    handler.post(new Runnable() {
                        public void run() {

                            //TODO CALL NOTIFICATION FUNC
//                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
//                                    .setSmallIcon(R.drawable.tactuslogo_small_round)
//                                    .setContentTitle("testnot")
//                                    .setContentText("lololololol")
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            showNotification("registreer uw gebruik", "u heeft al twee dagen niet uw gebruik geregistreerd");

                        }
                    });
                }
            };
        }
    void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "tactus",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.drawable.tactuslogo_small_round) // notification icon
                .setBadgeIconType(R.drawable.tactuslogo_small_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.tactuslogo_small_round))
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MyPersonalRiskActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
