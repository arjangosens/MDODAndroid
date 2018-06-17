package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.PhoneSettingsActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.adapters.riskAdapter.AsyncRisk;
import com.project.avans.mdodandroid.adapters.riskAdapter.OnAlertBoxAvailableR;
import com.project.avans.mdodandroid.adapters.riskAdapter.RiskListener;
import com.project.avans.mdodandroid.adapters.riskAdapter.OnRiskClick;
import com.project.avans.mdodandroid.adapters.riskAdapter.RiskAdapter;
import com.project.avans.mdodandroid.applicationLogic.ConnectionChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.Risk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPersonalRiskActivity extends AppCompatActivity implements DialogInterface.OnShowListener, RiskListener, OnAlertBoxAvailableR {

    private View updateDialogView;
    private ArrayList<Risk> RiskList = new ArrayList<>();
    private RiskAdapter RiskAdapter = null;
    private String url;
    private String type = "";
    private Risk riskup;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_risks);

        context = this;

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
            //Async API call
        url = "https://mdod.herokuapp.com/api/v1/risk";

        String[] urls = new String[] {url};
        AsyncRisk task = new AsyncRisk((RiskListener) this);
        task.execute(urls);}

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
        RiskListView.setOnItemClickListener(new OnRiskClick(this) {});
    }

    //Listens for updates
    @Override
    public void onRiskListener(Risk risk) {
        RiskList.add(risk);
        RiskAdapter.notifyDataSetChanged();

    }

    //Making of dialog box
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

                    if (ConnectionChecker.CheckCon(context)) {
                        Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
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

                    });}
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

    //Dialog box functionality
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
                    //Connection check
                    if (ConnectionChecker.CheckCon(context)) {
                        Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        if (type.equals("")) {
                            //API call
                            NetworkManager.getInstance().postRisk(updateDialogGenericEditText.getText().toString(), new VolleyListener<JSONObject>() {
                                @Override
                                public void getResult(JSONObject result) {

                                    String RiskId2 = "";
                                    if (!(result == null)) {
                                        try {
                                            RiskId2 = result.getString("riskId");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();
                                        //startService(new Intent(context, NotificationService.class));


                                        RiskList.add(new Risk(RiskId2, updateDialogGenericEditText.getText().toString()));
                                        RiskAdapter.notifyDataSetChanged();
                                    } else {
                                    }
                                }

                            });

                        } else if (type.equals("update")) {
                            NetworkManager.getInstance().updateRisk(updateDialogGenericEditText.getText().toString(), riskup, new VolleyListener<String>() {
                                @Override
                                public void getResult(String result) {
                                    if (!result.isEmpty()) {
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
            }
        });
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
                builder.setMessage(getResources().getString(R.string.logOut)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener2).setNegativeButton(getResources().getString(R.string.no), dialogClickListener2).show();
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


    //On update listener
    @Override
    public void onAlertBoxAvailableR(Risk risk) {
        Log.i("TEST: ", risk.toString());
        type = "update";
        this.riskup = risk;
        showUpdateDialog();
    }
}
