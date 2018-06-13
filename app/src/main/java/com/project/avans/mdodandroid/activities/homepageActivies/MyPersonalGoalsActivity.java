package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.adapters.goalAdapter.AsyncGoal;
import com.project.avans.mdodandroid.adapters.goalAdapter.GoalAdapter;
import com.project.avans.mdodandroid.adapters.goalAdapter.GoalListener;
import com.project.avans.mdodandroid.adapters.goalAdapter.OnAlertBoxAvailable;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.Goal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPersonalGoalsActivity extends AppCompatActivity implements DialogInterface.OnShowListener, GoalListener, OnAlertBoxAvailable {
    private View updateDialogView;
    private ArrayList<Goal> goalList = new ArrayList<>();
    private GoalAdapter goalAdapter = null;
    private String type = "";
    private Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_goals);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //removes the title from the title bar in My personal goals
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button add = findViewById(R.id.button_goals_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                showUpdateDialog();
            }
        });

        //TEST DATA API
        String url = "https://mdod.herokuapp.com/api/v1/goal";
//        "https://mdod.herokuapp.com/api/v1/goal

        String[] urls = new String[] {url};
        AsyncGoal task = new AsyncGoal((GoalListener) this);
        task.execute(urls);

        ListView goalListView = findViewById(R.id.listView);
        goalAdapter = new GoalAdapter(getApplicationContext(), this, getLayoutInflater(), goalList);
        goalListView.setAdapter(goalAdapter);
        goalAdapter.notifyDataSetChanged();
//        goalListView.setOnItemClickListener(new onGoalClick(getApplicationContext(), getLayoutInflater(), this));
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
            builder.setTitle(getResources().getString(R.string.newGoal));

        } else if (type.equals("goal")){
            builder.setTitle(getResources().getString(R.string.changeGoal));
            hint = goal.getGoal();
            final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

            updateDialogGenericEditText.setText(hint);

            builder.setNeutralButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, int id) {
                    NetworkManager.getInstance().deleteGoal(goal.getGoalID(),  new VolleyListener<JSONObject>(){
                        @Override
                        public void getResult(JSONObject result)
                        {
                            if (!(result == null))
                            {
                                goalList.remove(goal);
                                goalAdapter.notifyDataSetChanged();
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
                final TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
                final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

                String field = String.valueOf(updateDialogGenericEditText.getText());
                Log.i("DialogUpdateProfile", "Value of field: " + field);

                if (field.equals("")) {
                    incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));
                } else {

                    if (type.equals("")) {
                        NetworkManager.getInstance().postGoal(updateDialogGenericEditText.getText().toString(),  new VolleyListener<JSONObject>(){
                            @Override
                            public void getResult(JSONObject result)
                            {
                                if (!(result == null))
                                {
                                    String goalId = "";
                                    String status = "0";
                                    try {
                                        goalId = result.getString("goalId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    dialog.dismiss();
                                    goalList.add(new Goal(goalId, updateDialogGenericEditText.getText().toString(), status));
                                    goalAdapter.notifyDataSetChanged();
                                } else {
                                    incorrectFieldTextView.setText(getResources().getString(R.string.somethingWentWrong));
                                }
                            }

                        });

                    } else if (type.equals("goal")){
                        NetworkManager.getInstance().putGoal( goal.getGoalID(), updateDialogGenericEditText.getText().toString(),  new VolleyListener<JSONObject>(){
                            @Override
                            public void getResult(JSONObject result)
                            {
                                if (!(result == null))
                                {
                                    dialog.dismiss();
                                    goal.setGoal(updateDialogGenericEditText.getText().toString());
                                    goalAdapter.notifyDataSetChanged();
                                } else {
                                    incorrectFieldTextView.setText(getResources().getString(R.string.somethingWentWrong2));
                                }
                            }

                        });
                    }
                }
            }
        });
    }

    @Override
    public void onAlertBoxAvailable(Goal goal) {
        type = "goal";
        this.goal = goal;
        showUpdateDialog();
    }

    @Override
    public void onGoalListener(Goal goal) {
        goalList.add(goal);
        goalAdapter.notifyDataSetChanged();
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
                i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
