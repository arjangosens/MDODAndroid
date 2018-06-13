package com.project.avans.mdodandroid.activities.homepageActivies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.consumption.ConsumptionActivity;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.PhoneSettingsActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class HomepageActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        context = this;

        int color = Color.parseColor("#ffffff");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //removes the title from the title bar in the HomepageActivity
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setTitle(getResources().getString(R.string.homePageActivityHeader));
        //getSupportActionBar().setStackedBackgroundDrawable(getResources().getDrawable(R.drawable.tactuslogo_small));
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.tactuslogo_small));

        Button btn = (Button) findViewById(R.id.goalbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyPersonalGoalsActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button) findViewById(R.id.riskbutton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyPersonalRiskActivity.class);
                startActivity(i);
            }
        });

        Button btn3 = (Button) findViewById(R.id.situationbutton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyDifficultMomentsActivity.class);
                startActivity(i);
            }
        });

        Button btn4 = (Button) findViewById(R.id.call_button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PhoneActivity.class);
                startActivity(i);
            }
        });

        Button btn5 = (Button) findViewById(R.id.usagebutton);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConsumptionActivity.class);
                startActivity(i);
            }
        });

        Button btn6 = (Button) findViewById(R.id.messagesButton);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyMessagesActivity.class);
                startActivity(i);
            }
        });

        NetworkManager.getInstance().getCleanDays(new VolleyListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {
                TextView cleandays = findViewById(R.id.textView_clean);


                try {
                    if(object.getString("daysClean").equals("null")){
                        cleandays.setText("Nog geen gebruik geregistreerd");
                    }
                    else if(object.getString("daysClean").equals("0")) {
                        cleandays.setText("U had een terugval, neem contact op met uw begeleider.");

                    }
                    else
                    {
                        if(object.getString("daysClean").equals("30")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Al een maand niet gebruikt, ga zo door!")
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
                            builder.setMessage("Al een jaar clean we zijn trots!")
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
                            builder.setMessage("Een week clean, goed bezig!")
                                    .setCancelable(false)
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        cleandays.setText("Je bent al " + object.getString("daysClean") + " dagen clean.");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
    public void onBackPressed() {

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
                i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
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
}
