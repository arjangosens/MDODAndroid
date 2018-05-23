package com.project.avans.mdodandroid;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.project.avans.mdodandroid.applicationLogic.ValueChecker;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView settingsListview;

    private String[] settings = {"First Name", "insertion", "Last Name", "Date of birth", "Email", "Password"};

    private EditText updateDialogGenericEditText;
    private EditText updateDialogEmailEditText;
    private EditText updateDialogCurrentPasswordEditText;
    private EditText updateDialogNewPasswordEditText;
    private EditText updateDialogConfirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_settings);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);

        settingsListview.setAdapter(adapter);

        settingsListview.setOnItemClickListener(this);
    }

    private void showUpdateDialog(final String type) {
        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Change " + type);

        View view;

        switch (type) {
            case ("Email"):
                view = inflater.inflate(R.layout.dialog_updateprofile_email, null);
                builder.setView(view);
                break;

            case ("Password"):
                view = inflater.inflate(R.layout.dialog_updateprofile_password, null);
                builder.setView(view);
                break;

            default:
                view = inflater.inflate(R.layout.dialog_updateprofile, null);
                builder.setView(view);
                break;
        }

        final View finalView = view;
        builder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                updateDialogGenericEditText = finalView.findViewById(R.id.dialogUpdateProfile_editText);
                updateDialogEmailEditText = finalView.findViewById(R.id.dialogUpdateProfileEmail_editText);
                updateDialogCurrentPasswordEditText = finalView.findViewById(R.id.dialogUpdateProfilePassword_editTextCurrentPassword);
                updateDialogNewPasswordEditText = finalView.findViewById(R.id.dialogUpdateProfilePassword_editTextNewPassword);
                updateDialogConfirmPasswordEditText = finalView.findViewById(R.id.dialogUpdateProfilePassword_editTextConfirmPassword);

                boolean changeIsValid = false;

                switch (type) {
                    case ("Email"):
                        String email = String.valueOf(updateDialogEmailEditText.getText());
                        changeIsValid = ValueChecker.checkEmail(email);
                        break;

                    case ("Password"):
                        String currentPassword = String.valueOf(updateDialogCurrentPasswordEditText.getText());
                        String newPassword = String.valueOf(updateDialogNewPasswordEditText.getText());
                        String confirmPassword = String.valueOf(updateDialogConfirmPasswordEditText.getText());
                        changeIsValid = ValueChecker.checkPassword(currentPassword, newPassword, confirmPassword);
                        break;

                    default:
                        Log.i("DialogUpdateProfile", "editText: " + String.valueOf(R.id.dialogUpdateProfile_editText));
                        if (!String.valueOf(updateDialogGenericEditText.getText()).equals("")) {
                            changeIsValid = true;
                        }
                        break;
                }

                if (changeIsValid) {
                    Log.i("UserSettingsActivity", "Save changes  of " + type + " allowed");
                    // TODO: Save changes made in AlertDialog

                } else {
                    Log.i("UserSettingsActivity", "Save changes  of " + type + " NOT allowed");
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showUpdateDialog(settings[position]);

    }
}
