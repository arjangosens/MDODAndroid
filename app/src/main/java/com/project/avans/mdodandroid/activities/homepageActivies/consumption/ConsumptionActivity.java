package com.project.avans.mdodandroid.activities.homepageActivies.consumption;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.project.avans.mdodandroid.applicationLogic.notifications.NotificationService;
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

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        consumptionsPerDayArrayList = new ArrayList<>();
        date = Calendar.getInstance().getTime();
        
        getUsage();

        cpdListView = (ListView) findViewById(R.id.activityConsumption_listView);
        consumptionAdapter = new ConsumptionAdapter(getLayoutInflater(), consumptionsPerDayArrayList);
        cpdListView.setAdapter(consumptionAdapter);
        cpdListView.setOnItemClickListener(this);

        Button btn = (Button) findViewById(R.id.nothingUsedButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Goed gedaan, ga zo door!")
                        .setCancelable(false)
                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                NotificationService.Notificat(NotificationService.getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft.", context), 60 * 1000, context);
                                //NotificationService.Notificat(NotificationService.getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft.", context), 2*24*60*60*1000, context);
                                Intent i = new Intent(context, HomepageActivity.class);
                                startActivity(i);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                MediaPlayer applause= MediaPlayer.create(ConsumptionActivity.this, R.raw.applause);
                applause.start();
            }
        });

        registerButton = findViewById(R.id.activityConsumption_buttonRegisterConsumption);
        registerButton.setOnClickListener(this);
    }

    private void getUsage() {
        NetworkManager.getInstance().getUsage(new VolleyListener<JSONArray>() {
            @Override
            public void getResult(JSONArray result) {
                try {
                    if (result.length() > 0) {
                        for (int i = 0; i < result.length(); i++) {
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
                                        SimpleDateFormat compareDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String compareDate = compareDateFormat.format(parsedDate);

                                        for (ConsumptionsPerDay consumptionsPerDay : consumptionsPerDayArrayList) {
                                            String compareCpdDate = compareDateFormat.format(consumptionsPerDay.getDate());

                                            if (compareDate.equals(compareCpdDate)) {
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
                    e.printStackTrace();
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
}
