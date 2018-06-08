package com.project.avans.mdodandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.avans.mdodandroid.applicationLogic.ValueChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.object_classes.UserSettingsType;
import com.project.avans.mdodandroid.userSettingsAdapter.UserSettingsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhoneSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener {
    private ListView settingsListview;
    private ArrayList<UserSettingsType> settings = new ArrayList<>();
    private EditText updateDialogPhoneNrEditText;
    private UserSettingsType institution;
    private UserSettingsType doctor;
    private UserSettingsType buddy;
    private UserSettingsType ice;
    private UserSettingsAdapter userSettingsAdapter;
    private TextView incorrectPhoneNrTextView;
    private Integer id;
    private String type;
    private View updateDialogView;
    private Context context = this;
    int duration = Toast.LENGTH_LONG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_settings);


        //Get types
        initTypes();

        //removes the title from the title bar in the userSettingsActivity
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.phonenumberActivityHeader));

        //TODO: add local user data

        settings.add(institution);
        settings.add(doctor);
        settings.add(buddy);
        settings.add(ice);


        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_phone);
        userSettingsAdapter = new UserSettingsAdapter(getLayoutInflater(), settings);
        settingsListview.setAdapter(userSettingsAdapter);



        settingsListview.setOnItemClickListener(this);

        // Get user values
        getValues();
//        userSettingsAdapter.notifyDataSetChanged();
    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;
        String hint = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle(getResources().getString(R.string.change) + " " + type);

        View view;

        if (type.equals(institution.getType())) {
            view = inflater.inflate(R.layout.dialog_updateprofile_phonenumber, null);
            hint = institution.getValue();

            updateDialogPhoneNrEditText = view.findViewById(R.id.dialogUpdateProfilePhone_editText);
            updateDialogPhoneNrEditText.setHint(hint);

        } else if (type.equals(doctor.getType())) {
            return;
        }
        else if (type.equals(buddy.getType())) {
            view = inflater.inflate(R.layout.dialog_updateprofile_phonenumber, null);
            hint = buddy.getValue();

            updateDialogPhoneNrEditText = view.findViewById(R.id.dialogUpdateProfilePhone_editText);
            updateDialogPhoneNrEditText.setHint(hint);
        }
        else if (type.equals(ice.getType())) {
            view = inflater.inflate(R.layout.dialog_updateprofile_phonenumber, null);
            hint = ice.getValue();

            updateDialogPhoneNrEditText = view.findViewById(R.id.dialogUpdateProfilePhone_editText);
            updateDialogPhoneNrEditText.setHint(hint);
        }
        else{
            view = null;
        }

        builder.setView(view);

        updateDialogView = view;

        builder.setPositiveButton(getResources().getString(R.string.saveChanges), null);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        alertDialog.show();

        incorrectPhoneNrTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePhone_textView);


    }

    private void initTypes() {
        institution = new UserSettingsType(getResources().getString(R.string.institute));
        doctor = new UserSettingsType(getResources().getString(R.string.doctor));
        buddy = new UserSettingsType(getResources().getString(R.string.buddy));
        ice = new UserSettingsType(getResources().getString(R.string.ice));
    }

    private void getValues() {

//
//        // Fill types with test values
//        institution.setValue("+31612345678");
//        doctor.setValue("+31612345678");
//        buddy.setValue("+31612345678");
//        ice.setValue("+31612345678");
//        ((BaseAdapter) settingsListview.getAdapter()).notifyDataSetChanged();

        //TODO: Replace test values with actual API get call

        NetworkManager.getInstance().getClientPhone(new VolleyListener<JSONArray>() {
            @Override
            public void getResult(JSONArray result) {
                try{
                    if (result.length() > 0)
                    {
                        Log.i("VOLLEY_GETRESULT", "Result:" + result.toString());
                        try {
                            JSONObject resultObject = result.getJSONObject(0);
                            id = resultObject.getInt("id");
                            Log.i("phoneId: ", id.toString());
                            institution.setValue(resultObject.getString("PNfirm"));
                            doctor.setValue(resultObject.getString("phonenumber"));
                            buddy.setValue(resultObject.getString("PNbuddy"));
                            ice.setValue(resultObject.getString("PNice"));

                            ((BaseAdapter) settingsListview.getAdapter()).notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }}
                catch (NullPointerException e){
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.registerAlertDialogTitle), duration);
                    toast.show();
                }
            }
        });
    }

    private void updatePhone() {
        NetworkManager.getInstance().updatephone(institution.getValue(), doctor.getValue(), buddy.getValue(),
                ice.getValue(), id,  new VolleyListener<JSONObject>() {
                    @Override
                    public void getResult(JSONObject object) {

                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        type = settings.get(position).getType();
        showUpdateDialog();

    }

    @Override
    public void onShow(final DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean changeIsValid = false;
                String field = "";

                if (type.equals(institution.getType()) || type.equals(buddy.getType()) || type.equals(doctor.getType()) || type.equals(ice.getType())) {

                    field = String.valueOf(updateDialogPhoneNrEditText.getText());

                    if (ValueChecker.checkPhoneNumber(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectPhoneNrTextView.setText(getResources().getString(R.string.invalidPhoneNr));
                    }


                }

                else {
//                    Log.i("DialogUpdateProfile", "Default (else) called with type" + type);
//                    field = String.valueOf(updateDialogGenericEditText.getText());
                    changeIsValid = true;

                }

                if (changeIsValid)

                {
                    Log.i("UserSettingsActivity", "Save changes  of " + type + " allowed");
                    // TODO: Save changes made in AlertDialog

                    for (UserSettingsType userSettingsType : settings) {

                        if (userSettingsType.getType().contains(type)) {
                            userSettingsType.setValue(field);

                            ((BaseAdapter) settingsListview.getAdapter()).notifyDataSetChanged();
                            Log.i("UserSettingsActivity", type + " filled with " + field);

                            updatePhone();
                        }
                    }



                    dialog.dismiss();
                } else

                {
                    Log.i("UserSettingsActivity", "Save changes  of " + type + " NOT allowed");
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
        switch (id) {
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

}
