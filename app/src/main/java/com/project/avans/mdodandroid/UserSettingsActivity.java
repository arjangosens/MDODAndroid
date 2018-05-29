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

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener {
    private ListView settingsListview;
    private ArrayList<String> settings = new ArrayList<>();

    private EditText updateDialogGenericEditText;
    private EditText updateDialogPhoneNrEditText;
//    private EditText updateDialogCurrentPasswordEditText;
//    private EditText updateDialogNewPasswordEditText;
//    private EditText updateDialogConfirmPasswordEditText;
//
//    private TextView incorrectCurrentPasswordTextView;
//    private TextView incorrectNewPasswordTextView;
//    private TextView incorrectConfirmPasswordTextView;

    private String type_phoneNumber;
    private String type_firstName;
    private String type_insertion;
    private String type_lastName;

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

        type_firstName = getResources().getString(R.string.firstName);
        type_insertion = getResources().getString(R.string.Insertion);
        type_lastName = getResources().getString(R.string.Lastname);
//        String password = getResources().getString(R.string.password);
        String address = getResources().getString(R.string.adress);
        type_phoneNumber = getResources().getString(R.string.phoneNumber);

        //TODO: add local user data

        settings.add(type_firstName + ": user_firstName");
        settings.add(type_insertion + ": user_insertion");
        settings.add(type_lastName + ": user_lastName");
//        settings.add(password);
        settings.add(address);
        settings.add(type_phoneNumber);

        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_settings);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);

        settingsListview.setAdapter(adapter);

        settingsListview.setOnItemClickListener(this);
    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle(getResources().getString(R.string.change) + " " + type);

        View view;

        if (type.equals(type_phoneNumber)) {
            view = inflater.inflate(R.layout.dialog_updateprofile_phonenumber, null);

        } else {

            view = inflater.inflate(R.layout.dialog_updateprofile, null);

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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        type = settings.get(position);
        showUpdateDialog();

    }

    @Override
    public void onShow(final DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);
                incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
//                updateDialogCurrentPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextCurrentPassword);
//                updateDialogNewPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextNewPassword);
//                updateDialogConfirmPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextConfirmPassword);

//                incorrectCurrentPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePasswor_textViewIncorrectCurrentPassword);
//                incorrectNewPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectNewPassword);
//                incorrectConfirmPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectConfirmPassword);


                updateDialogPhoneNrEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePhone_editText);
                incorrectPhoneNrTextView =  updateDialogView.findViewById(R.id.dialogUpdateProfilePhone_textView);

                boolean changeIsValid = false;

                if (type.equals(type_firstName) || type.equals(type_lastName)) {
                    String field = String.valueOf(updateDialogGenericEditText.getText());
                    Log.i("DialogUpdateProfile", "Value of field: " + field);
                    if (field.equals("")) {
                        incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));

                    } else {
                        changeIsValid = true;
                    }

                } else if (type.equals(type_phoneNumber)) {

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
