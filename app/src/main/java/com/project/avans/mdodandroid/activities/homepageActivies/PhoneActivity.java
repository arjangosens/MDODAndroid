package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.PhoneSettingsActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.applicationLogic.ConnectionChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.PhoneNumbers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhoneActivity extends AppCompatActivity {
    Context context = this;
    PhoneNumbers numbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Connection check
        if (ConnectionChecker.CheckCon(context)) {
            Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else {
            //API call
        NetworkManager.getInstance().getClientPhone(new VolleyListener<JSONArray>() {
            @Override
            public void getResult(JSONArray result) {
                try {
                    String doctor;
                    if (result.length() > 0) {
                        Log.i("VOLLEY_GETRESULT", "Result:" + result.toString());
                        try {
                            JSONObject resultObject = result.getJSONObject(0);

                            String institution = (resultObject.getString("PNfirm"));

                            try {
                                doctor = (resultObject.getString("phonenumber"));
                            } catch (JSONException e) {
                                doctor = ("");

                            }
                            String buddy = (resultObject.getString("PNbuddy"));
                            String ice = (resultObject.getString("PNice"));

                            numbers = new PhoneNumbers(institution, doctor, buddy, ice);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (NullPointerException e) {
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.registerAlertDialogTitle), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });}


        //Buttons with check to evaluate if the fields aren't empty
        Button btn = (Button) findViewById(R.id.institute_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (numbers.getPhoneInstitute().equals("") || numbers.getPhoneInstitute().equals("null")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                    } else {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + numbers.getPhoneInstitute()));
                        startActivity(call);
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                }

            }
        });

        Button btn2 = (Button) findViewById(R.id.doctor_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (numbers.getPhoneDoctor().equals("") || numbers.getPhoneDoctor().equals("null")) {
                        Toast toast = Toast.makeText(context, getResources().getString(R.string.contactDr), Toast.LENGTH_LONG);
                        toast.show();

                    } else {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + numbers.getPhoneDoctor()));
                        startActivity(call);
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.contactDr), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Button btn3 = (Button) findViewById(R.id.buddy_button);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (numbers.getPhoneBuddy().equals("") || numbers.getPhoneBuddy().equals("null")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                    } else {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + numbers.getPhoneBuddy()));
                        startActivity(call);
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                }
            }
        });

        Button btn4 = (Button) findViewById(R.id.emergency_button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (numbers.getPhoneEmergency().equals("") || numbers.getPhoneEmergency().equals("null")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                    } else {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + numbers.getPhoneEmergency()));
                        startActivity(call);
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getResources().getString(R.string.numbernotfilled)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                }


            }
        });
    }



    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(getApplicationContext(), PhoneSettingsActivity.class);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    //Back button routing
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    //Custom menu functionality
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
                builder.setMessage(getResources().getString(R.string.used)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener2).setNegativeButton(getResources().getString(R.string.no), dialogClickListener2).show();
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
    DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
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
