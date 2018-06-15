package com.project.avans.mdodandroid.activities.homepageActivies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.consumption.ConsumptionActivity;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.PhoneSettingsActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.applicationLogic.ConnectionChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class HomepageActivity extends AppCompatActivity {
    private Context context;
    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        context = this;



        //custom toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Goals
        Button btn = (Button) findViewById(R.id.goalbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyPersonalGoalsActivity.class);
                startActivity(i);
            }
        });

        //Risks
        Button btn2 = (Button) findViewById(R.id.riskbutton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyPersonalRiskActivity.class);
                startActivity(i);
            }
        });

        //Difficult moment
        Button btn3 = (Button) findViewById(R.id.situationbutton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyDifficultMomentsActivity.class);
                startActivity(i);
            }
        });

        //Call
        Button btn4 = (Button) findViewById(R.id.call_button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PhoneActivity.class);
                startActivity(i);
            }
        });

        //Usage
        Button btn5 = (Button) findViewById(R.id.usagebutton);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConsumptionActivity.class);
                startActivity(i);
            }
        });

        //Messages
        Button btn6 = (Button) findViewById(R.id.messagesButton);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyMessagesActivity.class);
                startActivity(i);
            }
        });

        //Connection check
        if (ConnectionChecker.CheckCon(context)) {
            Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
            toast.show();
            TextView cleandays = findViewById(R.id.textView_clean);
            cleandays.setText(R.string.noInternet);
        } else {
        //API call
        NetworkManager.getInstance().getCleanDays(new VolleyListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {
                TextView cleandays = findViewById(R.id.textView_clean);

                //suprise messages
                try {
                    if(object.getString("daysClean").equals("null")){
                        cleandays.setText(R.string.noUsageRegistered);
                    }
                    else if(object.getString("daysClean").equals("0")) {
                        cleandays.setText(R.string.relapse);

                    }
                    else
                    {
                        if(object.getString("daysClean").equals("30")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(R.string.thirtyDays)
                                    .setCancelable(false)
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else if(object.getString("daysClean").equals("356")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(R.string.oneYear)
                                    .setCancelable(false)
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                        else if(object.getString("daysClean").equals("7")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(R.string.oneWeek)
                                    .setCancelable(false)
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                        if(object.getString("daysClean").equals("1")){
                            cleandays.setText(R.string.oneDay);
                        } else {
                            cleandays.setText(R.string.already + object.getString("daysClean") + R.string.daysClean);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });}
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    //Disables back button because of logout
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.backAgain,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    //Menu options
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
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setMessage(getResources().getString(R.string.used)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                break;
            case R.id.menu_user_phone:
                i = new Intent(getApplicationContext(),PhoneSettingsActivity.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Dialog Onclick
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
