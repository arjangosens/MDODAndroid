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
    private EditText updateDialogEmailEditText;
    private EditText updateDialogCurrentPasswordEditText;
    private EditText updateDialogNewPasswordEditText;
    private EditText updateDialogConfirmPasswordEditText;

    private TextView incorrectCurrentPasswordTextView;
    private TextView incorrectNewPasswordTextView;
    private TextView incorrectConfirmPasswordTextView;

    private TextView incorrectEmailTextView;
    private TextView incorrectFieldTextView;

    private String type;
    private View updateDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        String firstName = getResources().getString(R.string.firstName);
        String insertion = getResources().getString(R.string.Insertion);
        String lastName = getResources().getString(R.string.Lastname);
        String dateOfBirth = getResources().getString(R.string.Dateofbirth);
        String email = getResources().getString(R.string.Email);
        String password = getResources().getString(R.string.password);
        String adress = getResources().getString(R.string.adress);
        String phoneNumber = getResources().getString(R.string.phoneNumber);

        //TODO: add local user data

        settings.add(firstName);
        settings.add(insertion);
        settings.add(lastName);
        settings.add(dateOfBirth);
        settings.add(email);
        settings.add(password);
        settings.add(adress);
        settings.add(phoneNumber);

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

        switch (type) {
            case ("Emailadres"):
            case ("Email address"):
                view = inflater.inflate(R.layout.dialog_updateprofile_email, null);
                builder.setView(view);
                break;
            case ("Wachtwoord"):
            case ("Password"):
                view = inflater.inflate(R.layout.dialog_updateprofile_password, null);
                builder.setView(view);
                break;

            default:
                view = inflater.inflate(R.layout.dialog_updateprofile, null);
                builder.setView(view);
                break;
        }

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
                updateDialogEmailEditText = updateDialogView.findViewById(R.id.dialogUpdateProfileEmail_editText);
                updateDialogCurrentPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextCurrentPassword);
                updateDialogNewPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextNewPassword);
                updateDialogConfirmPasswordEditText = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_editTextConfirmPassword);

                incorrectCurrentPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePasswor_textViewIncorrectCurrentPassword);
                incorrectNewPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectNewPassword);
                incorrectConfirmPasswordTextView = updateDialogView.findViewById(R.id.dialogUpdateProfilePassword_textViewIncorrectConfirmPassword);

                incorrectEmailTextView = updateDialogView.findViewById(R.id.dialogUpdateProfileEmail_textViewIncorrectEmail);
                incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);

                boolean changeIsValid = false;

                switch (type) {
                    case ("Emailadres"):
                    case ("Email address"):
                        String email = String.valueOf(updateDialogEmailEditText.getText());
                        changeIsValid = ValueChecker.checkEmail(email);
                        if (!changeIsValid) {
                            incorrectEmailTextView.setText("Invalid email address!");
                        }
                        break;

                    case ("Wachtwoord"):
                    case ("Password"):
                        String currentPassword = String.valueOf(updateDialogCurrentPasswordEditText.getText());
                        String newPassword = String.valueOf(updateDialogNewPasswordEditText.getText());
                        String confirmPassword = String.valueOf(updateDialogConfirmPasswordEditText.getText());

                        String currentPasswordMsg;
                        String newPasswordMsg;
                        String confirmPasswordMsg;

                        changeIsValid = true;

                        if (!ValueChecker.checkCurrentPassword(currentPassword)) {
                            currentPasswordMsg = "Invalid password!";
                            changeIsValid = false;

                        } else {
                            currentPasswordMsg = "";
                        }

                        if (!ValueChecker.checkNewPasswordFormat(newPassword)) {
                            newPasswordMsg = "Needs to be longer than 8 characters and contain at least one number and letter";
                            changeIsValid = false;

                        } else {
                            newPasswordMsg = "";
                        }

                        if (!ValueChecker.checkConfirmMatchesNewPassword(newPassword, confirmPassword)) {
                            confirmPasswordMsg = "Needs to match new password";
                            changeIsValid = false;

                        } else {
                            confirmPasswordMsg = "";
                        }

                        incorrectCurrentPasswordTextView.setText(currentPasswordMsg);
                        incorrectNewPasswordTextView.setText(newPasswordMsg);
                        incorrectConfirmPasswordTextView.setText(confirmPasswordMsg);

                        break;

                    case ("First name"):
                    case ("Voornaam"):
                    case ("Last name"):
                    case ("Achternaam"):

                        String field = String.valueOf(updateDialogGenericEditText.getText());
                        Log.i("DialogUpdateProfile", "Value of field: " + field);
                        if (field.equals("")) {
                            incorrectFieldTextView.setText("Field cannot be empty!");

                        } else {
                            changeIsValid = true;
                        }
                        break;

                    default:
                        Log.i("DialogUpdateProfile", "Default called with type" + type);
                        changeIsValid = true;
                        break;
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
}
