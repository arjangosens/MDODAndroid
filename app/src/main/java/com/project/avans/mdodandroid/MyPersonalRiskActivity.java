package com.project.avans.mdodandroid;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.avans.mdodandroid.adapters.RiskAdapter.AsyncRisk;
import com.project.avans.mdodandroid.adapters.RiskAdapter.OnAlertBoxAvailableR;
import com.project.avans.mdodandroid.adapters.RiskAdapter.RiskListener;
import com.project.avans.mdodandroid.adapters.RiskAdapter.onRiskClick;
import com.project.avans.mdodandroid.adapters.RiskAdapter.RiskAdapter;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.object_classes.Risk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPersonalRiskActivity extends AppCompatActivity implements DialogInterface.OnShowListener, RiskListener, OnAlertBoxAvailableR {

    private View updateDialogView;
    private ArrayList<Risk> RiskList = new ArrayList<>();
    private RiskAdapter RiskAdapter = null;
    private String url;
    private String type = "";
    private Risk riskup;
    Context context;
    private String channelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_risks);

        context = this;
        //removes the title from the title bar in my personal risks
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Risk e = new Risk("1", "test");
        //RiskList.add(e);
        //getRisk();
        url = "https://mdod.herokuapp.com/api/v1/risk";

        String[] urls = new String[] {url};
        AsyncRisk task = new AsyncRisk((RiskListener) this);
        task.execute(urls);

        Button add = findViewById(R.id.button_risks);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                showUpdateDialog();
            }
        });

        ListView RiskListView = findViewById(R.id.listView_risks);
        RiskAdapter = new RiskAdapter(getLayoutInflater(), RiskList);
        RiskListView.setAdapter(RiskAdapter);
        RiskAdapter.notifyDataSetChanged();
        RiskListView.setOnItemClickListener(new onRiskClick(this) {});
    }

    @Override
    public void onRiskListener(Risk risk) {
        RiskList.add(risk);
        RiskAdapter.notifyDataSetChanged();

    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;
        String hint = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View view;

        view = inflater.inflate(R.layout.dialog_updateprofile, null);
        builder.setView(view);

        updateDialogView = view;

        if (type.equals("")) {
            builder.setTitle(getResources().getString(R.string.newRisk));
        } else if (type.equals("update")){
            builder.setTitle(getResources().getString(R.string.risk_update));
            hint = riskup.getRisk();
            final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);
            updateDialogGenericEditText.setText(hint);

            builder.setNeutralButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, int id) {
                    NetworkManager.getInstance().deleteRisk(riskup.getRiskID(),  new VolleyListener<JSONObject>(){
                        @Override
                        public void getResult(JSONObject result)
                        {
                            if (!(result == null))
                            {
                                RiskList.remove(riskup);
                                RiskAdapter.notifyDataSetChanged();
                                dialog.cancel();
                            } else {

                            }
                        }

                    });
                }
            });
        }

        builder.setPositiveButton(getResources().getString(R.string.saveChanges), null);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        alertDialog.show();
    }

    @Override
    public void onShow(final DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
                final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

                String field = String.valueOf(updateDialogGenericEditText.getText());
                Log.i("DialogUpdateProfile", "Value of field: " + field);
                if (field.equals("")) {
                    incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));

                } else {
                    if (type.equals("")) {
                        NetworkManager.getInstance().postRisk(updateDialogGenericEditText.getText().toString(),  new VolleyListener<JSONObject>(){
                            @Override
                            public void getResult(JSONObject result)
                            {

                                    String RiskId2 = "";
                                    if (!(result == null)){
                                        try {
                                            RiskId2 = result.getString("riskId");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();
                                        //startService(new Intent(context, NotificationService.class));
                                        Notificat(getNotification("notification"), 2*24*60*60*1000);

                                        RiskList.add(new Risk(RiskId2, updateDialogGenericEditText.getText().toString()));
                                        RiskAdapter.notifyDataSetChanged();
                                    } else {
                                    }
                                }

                            });

                        } else if (type.equals("update")) {
                            NetworkManager.getInstance().updateRisk(updateDialogGenericEditText.getText().toString(), riskup, new VolleyListener<String>(){
                                @Override
                                public void getResult(String result)
                                {
                                    if (!result.isEmpty())
                                    {
                                        System.out.println(RiskList.indexOf(riskup));
                                        dialog.dismiss();
                                        RiskList.get(RiskList.indexOf(riskup)).setRisk(updateDialogGenericEditText.getText().toString());
                                        RiskAdapter.notifyDataSetChanged();
                                    } else {
                                    }
                                }

                            });

                        }
                    }
                }
            });
                                  }

                //adds custom menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.settings_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            Intent i;
            switch(id){
                case R.id.menu_user_settings:
                    i = new Intent(getApplicationContext(), UserSettingsActivity.class);
                    startActivity(i);
                    break;
                case R.id.menu_logout:
                    i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            return true;
        }


        @Override
        public void onAlertBoxAvailableR(Risk risk) {
            Log.i("TEST: ", risk.toString());
            type = "update";
            this.riskup = risk;
            showUpdateDialog();
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
            Notification.Builder builder = new Notification.Builder(this.context, channelId);
            builder.setContentTitle("Scheduled Notification");
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.tactuslogo_small_round);
            return builder.build();
        }
        else{
            Notification.Builder builder = new Notification.Builder(this.context);
            builder.setContentTitle("Scheduled Notification");
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
