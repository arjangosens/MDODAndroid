package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.adapters.momentAdapter.MomentAdapter;
import com.project.avans.mdodandroid.domain.Moment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyDifficultMomentsActivity extends AppCompatActivity implements DialogInterface.OnShowListener, VolleyListener<JSONArray> {
    private View updateDialogView;
    private SeekBar seekBar;
    private ArrayList<Moment> moments = new ArrayList<>();
    private MomentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_difficult_moments);

        NetworkManager.getInstance().getMoment(this);

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Button add = findViewById(R.id.button_moments_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        ListView listView = findViewById(R.id.listView_moments);
        adapter = new MomentAdapter(this, getLayoutInflater(), moments);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showUpdateDialog() {
        final AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View view;

        view = inflater.inflate(R.layout.dialog_difficult_moment, null);
        builder.setView(view);

        updateDialogView = view;

        seekBar = updateDialogView.findViewById(R.id.seekBar_difficult_moment);
        seekBar.setMax(5);

        builder.setTitle(getResources().getString(R.string.newMoment));

        builder.setPositiveButton(getResources().getString(R.string.saveChanges), null);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        NetworkManager.getInstance().getSubstances(new VolleyListener<JSONArray>() {
            private ArrayList<String> substances = new ArrayList<>();
            private final Spinner spinner = updateDialogView.findViewById(R.id.spinner_moment);
            @Override
            public void getResult(JSONArray object) {
                try {
                    for (int i = 0;  i < object.length(); i++){
                        substances.add(object.getJSONObject(i).getString("name"));
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(alertDialog.getContext(), R.layout.support_simple_spinner_dropdown_item , substances);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        alertDialog.show();
    }

    @Override
    public void onShow(final DialogInterface dialog) {
        Log.i("TEST1234: ", "tetstets");
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogDifficultMoment_textViewIncorrectField);
                final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogDifficultMoment_editText);
                final TextView prevention = updateDialogView.findViewById(R.id.dialogDifficultMoment_editText_prevention);
                final Spinner spinner = updateDialogView.findViewById(R.id.spinner_moment);


                Log.i("TEST: ", String.valueOf(spinner.getSelectedItemPosition()));
                Log.i("TEST1234: ", String.valueOf(spinner.getSelectedItem()));


                String field = String.valueOf(updateDialogGenericEditText.getText());
                Log.i("DialogUpdateProfile", "Value of field: " + field);

                if (field.equals("")) {
                    incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));
                } else {
                    NetworkManager.getInstance().postMoment( String.valueOf(spinner.getSelectedItem()),String.valueOf(seekBar.getProgress()), String.valueOf(updateDialogGenericEditText.getText()), String.valueOf(prevention.getText()), new VolleyListener<JSONObject>(){
                        @Override
                        public void getResult(JSONObject result) {
                            if (!(result == null))
                            {
                                moments.add(0, new Moment(spinner.getSelectedItem().toString(), "zojuist", String.valueOf(updateDialogGenericEditText.getText()), seekBar.getProgress(), String.valueOf(prevention.getText())));
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                incorrectFieldTextView.setText(getResources().getString(R.string.somethingWentWrong));
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void getResult(JSONArray object) {
        if(!(object == null)) {
            for (int i = 0; i < object.length(); i++) {
                try {
                    String date = object.getJSONObject(i).getString("date_lust");
                    String[] parts = date.split("[-T.]+");
                    Log.i("TESTT: ", String.valueOf(parts.length));
                    String date2 = parts[2] + "-" + parts[1] + "-" + parts[0] + " " + parts[3];

                    if (object.getJSONObject(i).getString("prevention").equals("null")){
                        moments.add(new Moment(object.getJSONObject(i).getString("name"), date2, object.getJSONObject(i).getString("description"), object.getJSONObject(i).getInt("lust"), "Ik heb niks gedaan om gebruik te voorkomen"));
                    } else {
                        moments.add(new Moment(object.getJSONObject(i).getString("name"), date2, object.getJSONObject(i).getString("description"), object.getJSONObject(i).getInt("lust"), object.getJSONObject(i).getString("prevention")));
                    }
                    adapter.notifyDataSetChanged();
                    Log.i("TESTTT: ", moments.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomepageActivity.class);
        startActivity(i);
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

