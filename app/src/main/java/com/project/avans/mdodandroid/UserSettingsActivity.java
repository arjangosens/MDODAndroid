package com.project.avans.mdodandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.applicationLogic.ValueChecker;
import com.project.avans.mdodandroid.object_classes.UserSettingsType;

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener {
    private ListView settingsListview;
    private ArrayList<UserSettingsType> settings = new ArrayList<>();

    private EditText updateDialogGenericEditText;
    private EditText updateDialogPhoneNrEditText;
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

    private TextView incorrectFieldTextView;
    private TextView incorrectPhoneNrTextView;

    private String type;
    private View updateDialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //removes the title from the title bar in the userSettingsActivity
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firstName = new UserSettingsType(getResources().getString(R.string.firstName));
        insertion = new UserSettingsType(getResources().getString(R.string.Insertion));
        lastName = new UserSettingsType(getResources().getString(R.string.Lastname));
//        String password = getResources().getString(R.string.password);
        address = new UserSettingsType(getResources().getString(R.string.adress));
        phoneNumber = new UserSettingsType(getResources().getString(R.string.phoneNumber));


        // Test values to check hints
        firstName.setValue("John");
        lastName.setValue("Doe");
        phoneNumber.setValue("+31612345678");
        address.setValue("Lovensdijkstraat 61, Breda");

        //TODO: add local user data

        settings.add(firstName);
        settings.add(insertion);
        settings.add(lastName);
//        settings.add(password);
        settings.add(address);
        settings.add(phoneNumber);

        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_settings);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, settings);

        settingsListview.setAdapter(adapter);

        settingsListview.setOnItemClickListener(this);
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

        } else {

            view = inflater.inflate(R.layout.dialog_updateprofile, null);
            updateDialogGenericEditText = view.findViewById(R.id.dialogUpdateProfile_editText);

            if (type.equals(firstName.getType())) {
                hint = firstName.getValue();

            } else if (type.equals(lastName.getType())) {
                hint = lastName.getValue();

            } else if (type.equals(address.getType())) {
                hint = address.getValue();
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

        incorrectPhoneNrTextView =  updateDialogView.findViewById(R.id.dialogUpdateProfilePhone_textView);


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

                if (type.equals(firstName.getType()) || type.equals(lastName.getType())) {

                    String field = String.valueOf(updateDialogGenericEditText.getText());
                    Log.i("DialogUpdateProfile", "Value of field: " + field);

                    if (field.equals("")) {
                        incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));

                    } else {
                        changeIsValid = true;
                    }

                } else if (type.equals(phoneNumber.getType())) {

                    if (ValueChecker.checkPhoneNumber(String.valueOf(updateDialogPhoneNrEditText.getText()))) {
                        changeIsValid = true;

                    } else {
                        incorrectPhoneNrTextView.setText(getResources().getString(R.string.invalidPhoneNr));
                    }


                } else {
                    Log.i("DialogUpdateProfile", "Default (else) called with type" + type);
                    changeIsValid = true;

                }

                if (changeIsValid)

                {
                    Log.i("UserSettingsActivity", "Save changes  of " + type + " allowed");
                    // TODO: Save changes made in AlertDialog

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
        switch(id){
            case R.id.menu_user_settings:
//                i = new Intent(getApplicationContext(), UserSettingsActivity.class);
//                startActivity(i);
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
