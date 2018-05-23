package com.project.avans.mdodandroid;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button registerButton;
    private Button datePickerButton;
    private EditText firstNameEditText;
    private EditText insertionEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private String dateOfBirth;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.activityRegister_buttonCreateAccount);
        datePickerButton = findViewById(R.id.activityRegister_buttonDateOfBirth);

        registerButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);

        firstNameEditText = findViewById(R.id.activityRegister_editTextFirstname);
        insertionEditText = findViewById(R.id.activityRegister_editTextInsertion);
        lastNameEditText = findViewById(R.id.activityRegister_editTextLastname);

        emailEditText = findViewById(R.id.activityRegister_editTextEmail);

        passwordEditText = findViewById(R.id.activityRegister_editTextPassword);
        confirmPasswordEditText = findViewById(R.id.activityRegister_editTextConfirmPassword);

        dateOfBirth = "";
        email = "";


    }

    private boolean checkPassword() {
        String pw = String.valueOf(passwordEditText.getText());
        String cPw = String.valueOf(confirmPasswordEditText.getText());

        if (pw.length() >= 8) {

            if (pw.equals(cPw)) {
                Log.i("RegisterActivity", "checkPassword() passwords are equal");
                return true;

            } else {
                Log.i("RegisterActivity", "checkPassword() passwords are NOT equal");
                return false;

            }

        } else {
            Log.i("RegisterActivity", "checkPassword() password length is too short");
            return false;
        }
    }

    private boolean checkIfEmptyFields() {
        if (String.valueOf(firstNameEditText.getText()).isEmpty() || String.valueOf(lastNameEditText).isEmpty()
                || dateOfBirth.length() <= 0) {

            Log.i("RegisterActivity", "checkIfEmptyFields some fields are empty");
            return false;

        } else {
            Log.i("RegisterActivity", "checkIfEmptyFields fields are not empty");
            return true;

        }
    }

    private boolean checkEmail() {
        email = String.valueOf(emailEditText.getText());
        Pattern emailRegex =
                Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

            Matcher matcher = emailRegex .matcher(email);

            boolean result = matcher.find();
            Log.i("RegisterActivity", "checkEmail email is " + result);

            return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activityRegister_buttonDateOfBirth:
                Log.i("RegisterActivity", "onClick of datePickerButton called");

                DatePickerDialog dialog = new DatePickerDialog(this, this, 2018, 0, 1);
                dialog.show();

                break;

            case R.id.activityRegister_buttonCreateAccount:
                Log.i("RegisterActivity", "onClick of registerButton called");

                if (checkPassword() && checkIfEmptyFields() && checkEmail()) {
                    //TODO: Create intent to dashboard with new account

                } else {
                    //TODO: Notify user that passwords do not match

                }


                //TODO: Pass data through API and check if data is correct
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;

        dateOfBirth = (dayOfMonth + "-" + month + "-" + year);
        Log.i("RegisterActivity", "onDateSet called, date: " + dateOfBirth);
        datePickerButton.setText(dateOfBirth);


        //TODO: Pass date of birth through with the rest, and possibly add a check for invalid dates (like in the future)
    }
}
