package com.project.avans.mdodandroid.activities.homepageActivies.consumption;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.project.avans.mdodandroid.activities.homepageActivies.HomepageActivity;
import com.project.avans.mdodandroid.applicationLogic.notifications.NotificationPublisher;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.adapters.consumptionAdapter.ConsumptionAdapter;
import com.project.avans.mdodandroid.domain.Consumption;
import com.project.avans.mdodandroid.domain.ConsumptionsPerDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsumptionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ConsumptionAdapter consumptionAdapter;
    private ArrayList<ConsumptionsPerDay> consumptionsPerDayArrayList;
    private Date date;
    public final static String CONSUMPTIONSPD = "consumptionsPerDay";

    private ListView cpdListView;
    private Button registerButton;
    Context context;
    private String channelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        context = this;

        consumptionsPerDayArrayList = new ArrayList<>();
        date = Calendar.getInstance().getTime();

        // Create test data
        getUsage();


        cpdListView = (ListView) findViewById(R.id.activityConsumption_listView);
        consumptionAdapter = new ConsumptionAdapter(getLayoutInflater(), consumptionsPerDayArrayList);
        cpdListView.setAdapter(consumptionAdapter);
        cpdListView.setOnItemClickListener(this);

        Button btn = (Button) findViewById(R.id.nothingUsedButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notificat(getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft."), 60 * 1000);//for showing

            }
        });

        registerButton = findViewById(R.id.activityConsumption_buttonRegisterConsumption);
        registerButton.setOnClickListener(this);
    }

    private void getUsage() {
        //Create test data (for now)
//        ConsumptionsPerDay cpdToday = new ConsumptionsPerDay(date);
//        Consumption consumption = new Consumption(date, "Alcohol", 5, "idk", 1);
//        cpdToday.add(consumption);
//
//        date = Calendar.getInstance().getTime();
//
//        consumption = new Consumption(date, "Weed", 5, "idk2", 2);
//        cpdToday.add(consumption);
//
//
//        consumptionsPerDayArrayList.add(cpdToday);

        NetworkManager.getInstance().getUsage(new VolleyListener<JSONArray>() {
            @Override
            public void getResult(JSONArray result) {
                try {
                    if (result.length() > 0) {
                        for (int i = 0; i < result.length() - 1; i++) {
                            Log.i("VOLLEY_GETRESULT", "Result:" + result.toString());
                            try {
                                JSONObject resultObject = result.getJSONObject(i);

                                String name = resultObject.getString("name");
                                String measurement = resultObject.getString("measuringUnit");
                                String location = resultObject.getString("location");
                                String cause = resultObject.getString("cause");
                                int amount = resultObject.getInt("amount");
                                int mood = resultObject.getInt("mood");
                                String date = resultObject.getString("usedDate_formatted");

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH-mm");
                                Date parsedDate = null;

                                try {
                                    parsedDate = dateFormat.parse(date);
                                    Log.i("ConsumptionAct", "date: " + parsedDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Consumption consumption;

                                if (parsedDate != null) {
                                    consumption = new Consumption(parsedDate, name, amount, location, cause, mood, measurement);
                                } else {
                                    Log.i("ConsumptionAct", "Date not parsed!");
                                    consumption = null;
                                }

                                if (consumption != null) {
                                    if (!consumptionsPerDayArrayList.isEmpty()) {

                                        boolean isAdded = false;

                                        for (ConsumptionsPerDay consumptionsPerDay : consumptionsPerDayArrayList) {

                                            //Uses deprecated methods, should be changed in the future
                                            if (consumptionsPerDay.getDate().getYear() == consumption.getDate().getYear()
                                                    && consumptionsPerDay.getDate().getMonth() == consumption.getDate().getMonth()
                                                    && consumptionsPerDay.getDate().getDay() == consumption.getDate().getDay()) {

                                                consumptionsPerDay.add(consumption);
                                                Log.i("ConsumptionAct", "consumption added to existing cpd!");
                                                isAdded = true;
                                            }
                                        }

                                        if (!isAdded) {
                                            ConsumptionsPerDay consumptionsPerDay = new ConsumptionsPerDay(consumption.getDate());
                                            Log.i("ConsumptionAct", "cpd not found!, created new one");
                                            consumptionsPerDay.add(consumption);
                                            consumptionsPerDayArrayList.add(consumptionsPerDay);

                                        }

                                    } else {
                                        ConsumptionsPerDay consumptionsPerDay = new ConsumptionsPerDay(consumption.getDate());
                                        Log.i("ConsumptionAct", "cpd not found!, created new one");
                                        consumptionsPerDay.add(consumption);
                                        consumptionsPerDayArrayList.add(consumptionsPerDay);

                                    }
                                }

                                ((BaseAdapter) cpdListView.getAdapter()).notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                } catch (NullPointerException e) {
//                    Toast toast = Toast.makeText(context, getResources().getString(R.string.registerAlertDialogTitle), duration);
//                    toast.show();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), ConsumptionSpecificDayActivity.class);

        ConsumptionsPerDay consumptionsPerDay = consumptionsPerDayArrayList.get(position);
        i.putExtra(CONSUMPTIONSPD, consumptionsPerDay);

        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterConsumptionActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    public void Notificat(Notification notification, int delay) {


        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            channel();
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
    private void channel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

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
