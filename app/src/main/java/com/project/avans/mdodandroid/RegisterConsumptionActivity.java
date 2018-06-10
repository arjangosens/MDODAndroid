package com.project.avans.mdodandroid;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.consumptionAdapter.ConRegSubstanceAdapter;
import com.project.avans.mdodandroid.object_classes.Substance;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegisterConsumptionActivity extends AppCompatActivity implements ConRegSubstanceAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView substanceRv;

    private TextView typetextView;
    private TextView LocationTextView;
    private TextView causeTextView;
    private TextView unitTextView;

    private TextView amountTextView;


    private ArrayList<Substance> substances;
    private ArrayList<ImageView> smileys;
    private Integer substanceId;

    private Integer feelingId;
    private ImageView smileyHappy;
    private ImageView smileyGood;
    private ImageView smileyOk;
    private ImageView smileySad;
    private ImageView smileyTerrible;
    String substanceStr;
    Context context;
    private String channelId;

    private ConRegSubstanceAdapter adapter;
    private static final String TAG = "RegConsumptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumption);

        context = this;
        substances = new ArrayList<>();
        smileys = new ArrayList<>();

        initTypes();
        initSmileys();


        substanceRv = (RecyclerView) findViewById(R.id.act_registerConsumption_RecyclerViewSubstances);
        typetextView = (TextView) findViewById(R.id.act_registerConsumption_textViewSelectionValue);
        LocationTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextLocationValue);
        causeTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextCauseValue);
        amountTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextAmountValue);
        unitTextView = (TextView)findViewById(R.id.act_unit_textview);




//        adapter = new ConRegSubstanceAdapter(substances);
//        adapter.setOnItemClickListener(this);
//        substanceRv.setAdapter(adapter);

        substanceRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.adapter = new ConRegSubstanceAdapter(substances, new ConRegSubstanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(TAG, "onItemClick(" + position + ") called");

                Substance substance = substances.get(position);
                typetextView.setText(substance.getType());
                substanceStr = String.valueOf(typetextView.getText());
                initUnit(substance.getMeasurement());


            }
        });
        substanceRv.setAdapter(this.adapter);

        Button btn = (Button) findViewById(R.id.registerUsageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO checks for empty fields

                String location = String.valueOf(LocationTextView.getText());
                String cause = String.valueOf(causeTextView.getText());
                String amountTmp = String.valueOf(amountTextView.getText());
                Integer amount = Integer.parseInt(amountTmp);

                if(!substanceStr.equals("") && !location.equals("") && !cause.equals("") && feelingId != null){

                    Log.i("data, ",location + " " + amount + " " + feelingId +  " " + cause + " " + substanceId );

                        NetworkManager.getInstance().postUsage(location,amount,feelingId,cause,substanceId, new VolleyListener<JSONObject>() {
                            @Override
                            public void getResult(JSONObject object) {
                                try {
                                    Log.i("TEST: ", object.toString());

                                    if (!(object == null)) {
                                        Intent i = new Intent(getApplicationContext(), ConsumptionActivity.class);

                                        //Notificat(getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft."), 2*24*60*60*1000);//for real
                                        Notificat(getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft."), 60 * 1000);//for showing
                                        startActivity(i);


                                    } else {
                                        //TODO error message
                                    }
                                }
                                catch (NullPointerException e){
                                    //TODO iets mis met de server
                                    e.printStackTrace();
                                }
                            }
                        });

                }




            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        substanceRv.setLayoutManager(llm);

    }

    @Override
    public void onItemClick(int position) {
//        substanceRv.getRecycledViewPool().clear();
        Log.i(TAG, "OnItemClick() called on pos " + position);
    }

    private void initTypes() {
        //substances.add(new Substance("Nothing", getResources().getDrawable(R.drawable.like), ""));
        substances.add(new Substance("Alcohol", getResources().getDrawable(R.drawable.wine), "ml"));
        substances.add(new Substance("Weed", getResources().getDrawable(R.drawable.marijuana), "g"));
        substances.add(new Substance("GHB", getResources().getDrawable(R.drawable.ghb), "qwerty"));
        substances.add(new Substance("LSD", getResources().getDrawable(R.drawable.lsd), "mg"));
        substances.add(new Substance("Cocaine", getResources().getDrawable(R.drawable.cocaine), "lines"));
        substances.add(new Substance("Other", getResources().getDrawable(R.drawable.question), ""));
    }

    private void initUnit(String unit) {

        Log.i("substance Id: ", substanceStr);

        unitTextView.setText(unit);
        if(substanceStr.equals("Nothing")){
            substanceId = 12;
        }
        else if (substanceStr.equals("Alcohol")){
            Log.i("Alcohol", "chosen");
            substanceId = 1;
        }
        else if (substanceStr.equals("Weed")){
            substanceId = 2;
        }
        else if (substanceStr.equals("GHB")){
            substanceId = 8;
        }
        else if (substanceStr.equals("LSD")){
            substanceId = 9;
        }
        else if (substanceStr.equals("Cocaine")) {
            substanceId = 10;
        }
        else if (substanceStr.equals("Other")){
            substanceId = 11;
        }
    }

    private void initSmileys() {
        smileyHappy = (ImageView) findViewById(R.id.con_smiley_happy);
        smileyGood = (ImageView) findViewById(R.id.con_smiley_good);
        smileyOk = (ImageView) findViewById(R.id.con_smiley_ok);
        smileySad = (ImageView) findViewById(R.id.con_smiley_sad);
        smileyTerrible = (ImageView) findViewById(R.id.con_smiley_terrible);

        smileys.add(smileyHappy);
        smileys.add(smileyGood);
        smileys.add(smileyOk);
        smileys.add(smileySad);
        smileys.add(smileyTerrible);

        for (ImageView smiley : smileys) {
            smiley.setOnClickListener(this);
            smiley.setColorFilter(getResources().getColor(android.R.color.black));
        }

    }

    private void selectSmiley(int position) {
        ImageView selectedSmiley = smileys.get(position);

        for (ImageView smiley : smileys) {
            if (smiley.equals(selectedSmiley)) {
                smiley.clearColorFilter();

            } else {
                smiley.setColorFilter(getResources().getColor(android.R.color.black));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Onclick called");

        switch (v.getId()) {
            case R.id.con_smiley_happy:
                selectSmiley(0);
                feelingId = 5;
                break;

            case R.id.con_smiley_good:
                selectSmiley(1);
                feelingId = 4;
                break;

            case R.id.con_smiley_ok:
                selectSmiley(2);
                feelingId = 3;
                break;

            case R.id.con_smiley_sad:
                selectSmiley(3);
                feelingId = 2;
                break;

            case R.id.con_smiley_terrible:
                selectSmiley(4);
                feelingId = 1;
                break;
        }
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
