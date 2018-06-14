package com.project.avans.mdodandroid.activities.settingActivities;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.applicationLogic.ValueChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.UserSettingsType;
import com.project.avans.mdodandroid.adapters.userSettingsAdapter.UserSettingsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener, Button.OnClickListener {
    private ListView settingsListview;
    private ArrayList<UserSettingsType> settings = new ArrayList<>();

    private EditText updateDialogGenericEditText;
    private EditText updateDialogPhoneNrEditText;
    private EditText updateDialogZipCodeEditText;
//    private EditText updateDialogCurrentPasswordEditText;
//    private EditText updateDialogNewPasswordEditText;
//    private EditText updateDialogConfirmPasswordEditText;
//
//    private TextView incorrectCurrentPasswordTextView;
//    private TextView incorrectNewPasswordTextView;
//    private TextView incorrectConfirmPasswordTextView;

    private UserSettingsType phoneNumber;
    private UserSettingsType firstName;
    private UserSettingsType insertion;
    private UserSettingsType lastName;
    private UserSettingsType address;
    private UserSettingsType city;
    private UserSettingsType zipCode;
    private UserSettingsType birthday;

    private UserSettingsAdapter userSettingsAdapter;

    private TextView incorrectFieldTextView;
    private TextView incorrectPhoneNrTextView;
    private TextView incorrectZipCodeTextView;

    private Button deleteAccountButton;

    private String type;
    private View updateDialogView;
    private Context context = this;

    int duration = Toast.LENGTH_LONG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //Get types
        initTypes();

        //removes the title from the title bar in the userSettingsActivity
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.profile));

        //TODO: add local user data

        settings.add(firstName);
        settings.add(insertion);
        settings.add(lastName);
//        settings.add(password);
        settings.add(city);
        settings.add(address);
        settings.add(zipCode);
        settings.add(phoneNumber);


        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_settings);
        userSettingsAdapter = new UserSettingsAdapter(getLayoutInflater(), settings);
        settingsListview.setAdapter(userSettingsAdapter);

        deleteAccountButton = (Button) findViewById(R.id.activityUserSettings_buttonDeleteAccount);

        settingsListview.setOnItemClickListener(this);
        deleteAccountButton.setOnClickListener(this);

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

        if (type.equals(phoneNumber.getType())) {
            view = inflater.inflate(R.layout.dialog_updateprofile_phonenumber, null);
            hint = phoneNumber.getValue();

            updateDialogPhoneNrEditText = view.findViewById(R.id.dialogUpdateProfilePhone_editText);
            updateDialogPhoneNrEditText.setHint(hint);

        } else if (type.equals(zipCode.getType())) {
            view = inflater.inflate(R.layout.dialog_updateprofile_zipcode, null);
            hint = zipCode.getValue();

            updateDialogZipCodeEditText = view.findViewById(R.id.dialogUpdateProfileZipCode_editText);
            updateDialogZipCodeEditText.setHint(hint);
        } else {

            view = inflater.inflate(R.layout.dialog_updateprofile, null);
            updateDialogGenericEditText = view.findViewById(R.id.dialogUpdateProfile_editText);

            if (type.equals(firstName.getType())) {
                hint = firstName.getValue();

            } else if (type.equals(lastName.getType())) {
                hint = lastName.getValue();

            } else if (type.equals(address.getType())) {
                hint = address.getValue();

            } else if (type.equals(insertion.getType())) {
                hint = insertion.getValue();

            } else if (type.equals(city.getType())) {
                hint = city.getValue();
            }

            updateDialogGenericEditText.setHint(hint);
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

        incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);

        incorrectPhoneNrTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePhone_textView);

        incorrectZipCodeTextView = updateDialogView.findViewById(R.id.dialogUpdateProfileZipCode_textViewIncorrectValue);


    }

    private void initTypes() {
        firstName = new UserSettingsType(getResources().getString(R.string.firstName));
        insertion = new UserSettingsType(getResources().getString(R.string.Insertion));
        lastName = new UserSettingsType(getResources().getString(R.string.Lastname));
//        String password = getResources().getString(R.string.password);
        address = new UserSettingsType(getResources().getString(R.string.adress));
        phoneNumber = new UserSettingsType(getResources().getString(R.string.phoneNumber));
        city = new UserSettingsType(getResources().getString(R.string.city));
        zipCode = new UserSettingsType(getResources().getString(R.string.zipCode));
        birthday = new UserSettingsType("birthday");
    }

    private void getValues() {

//
//        // Fill types with test values
//        firstName.setValue("John");
//        lastName.setValue("Doe");
//        phoneNumber.setValue("+31612345678");
//        address.setValue("Lovensdijkstraat 61, Breda");

        //TODO: Replace test values with actual API get call

        NetworkManager.getInstance().getClient(new VolleyListener<JSONArray>() {
            @Override
            public void getResult(JSONArray result) {
                try{
                    if (result.length() > 0)
                    {
                        Log.i("VOLLEY_GETRESULT", "Result:" + result.toString());
                        try {
                            JSONObject resultObject = result.getJSONObject(0);
                            firstName.setValue(resultObject.getString("firstname"));
                            insertion.setValue(resultObject.getString("infix"));
                            lastName.setValue(resultObject.getString("lastname"));
                            phoneNumber.setValue(resultObject.getString("phonenumber"));
                            address.setValue(resultObject.getString("adress"));
                            city.setValue(resultObject.getString("city"));
                            zipCode.setValue(resultObject.getString("zipcode"));

                            String dobString = resultObject.getString("birthday");
                            String[] splitDobString = dobString.split("T");
                            dobString = splitDobString[0];
                            birthday.setValue(dobString);

//                        Log.i("BIRTHDAYVALUE", birthday.getValue());

                            ((BaseAdapter) settingsListview.getAdapter()).notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }catch (NullPointerException e) {
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.registerAlertDialogTitle), duration);
                    toast.show();
                }
                }
            });
        }

        private void updateClient() {
            NetworkManager.getInstance().updateClient(firstName.getValue(), insertion.getValue(), lastName.getValue(),
                    phoneNumber.getValue(), birthday.getValue(), city.getValue(), address.getValue(), zipCode.getValue(), new VolleyListener<JSONObject>() {
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

//                updateDialogCurrentPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextCurrentPassword);
//                updateDialogNewPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextNewPassword);
//                updateDialogConfirmPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextConfirmPassword);

//                incorrectCurrentPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePasswor_textViewIncorrectCurrentPassword);
//                incorrectNewPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectNewPassword);
//                incorrectConfirmPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectConfirmPassword);


                boolean changeIsValid = false;
                String field = "";

                if (type.equals(firstName.getType()) || type.equals(lastName.getType())) {

                    field = String.valueOf(updateDialogGenericEditText.getText());
                    Log.i("DialogUpdateProfile", "Value of field: " + field);

                    if (ValueChecker.checkName(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectFieldTextView.setText(getResources().getString(R.string.invalidName));
                    }

                } else if (type.equals(insertion.getType())) {
                    field = String.valueOf(updateDialogGenericEditText.getText());

                    if (ValueChecker.checkInsertion(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectFieldTextView.setText(getResources().getString(R.string.invalidName));
                    }



                } else if (type.equals(phoneNumber.getType())) {

                    field = String.valueOf(updateDialogPhoneNrEditText.getText());

                    if (ValueChecker.checkPhoneNumber(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectPhoneNrTextView.setText(getResources().getString(R.string.invalidPhoneNr));
                    }


                } else if (type.equals(zipCode.getType())) {

                    field = String.valueOf(updateDialogZipCodeEditText.getText());

                    if (ValueChecker.checkZipCode(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectZipCodeTextView.setText(getResources().getString(R.string.invalidZipCode));
                    }

                } else if (type.equals(city.getType())) {
                    field = String.valueOf(updateDialogGenericEditText.getText());

                    if (ValueChecker.checkCity(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectFieldTextView.setText(getResources().getString(R.string.invalidCity));
                    }

                } else if (type.equals(address.getType())) {
                    field = String.valueOf(updateDialogGenericEditText.getText());

                    if (ValueChecker.checkAddress(field)) {
                        changeIsValid = true;

                    } else {
                        incorrectFieldTextView.setText(getResources().getString(R.string.invalidAddress));
                    }

                } else {
                    Log.i("DialogUpdateProfile", "Default (else) called with type" + type);
                    field = String.valueOf(updateDialogGenericEditText.getText());
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

                            updateClient();
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
//                i = new Intent(getApplicationContext(), UserSettingsActivity.class);
//                startActivity(i);
                break;
            case R.id.menu_logout:
                i = new Intent(getApplicationContext(), LoginActivity.class);
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

    @Override
    public void onClick(View v) {
        Log.i("UserSettingsActivity", "Onclick of delete account button called");

        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.deleteAccountTitle));
        builder.setMessage(getResources().getString(R.string.deleteAccountMessage));

        builder.setCancelable(false);
        builder.setNegativeButton(getResources().getString(R.string.deleteAccountAbort), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("US_DELETEACCOUNT", "Deletion of account aborted");
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getResources().getString(R.string.deleteAccountConfirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("US_DELETEACCOUNT", "Deletion of account confirmed");
                NetworkManager.getInstance().deleteClient(new VolleyListener<JSONObject>() {
                    @Override
                    public void getResult(JSONObject object) {

                    }
                });

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }
}
