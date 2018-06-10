package com.project.avans.mdodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //removes the title from the title bar in the HomepageActivity
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.homePageActivityHeader));

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
                Intent i = new Intent(getApplicationContext(), MyDifficultMoments.class);
                startActivity(i);
            }
        });

        Button btn4 = (Button) findViewById(R.id.call_button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), phoneActivity.class);
                startActivity(i);
            }
        });

        Button btn5 = (Button) findViewById(R.id.usagebutton);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), phoneActivity.class);
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
                    } else{
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
