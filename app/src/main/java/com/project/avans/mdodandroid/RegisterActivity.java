package com.project.avans.mdodandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
    private TextView validCredentials;
    private String dateOfBirth;
    private String email;
    private String Token;

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

        validCredentials = findViewById(R.id.activityRegister_validCredentials);

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, 2018, 0, 1);
                datePickerDialog.show();

                break;

            case R.id.activityRegister_buttonCreateAccount:
                Log.i("RegisterActivity", "onClick of registerButton called");

                boolean validPassword = checkPassword();
                boolean fieldsNotEmpty = checkIfEmptyFields();
                boolean validEmail = checkEmail();

                if (validPassword && validEmail && fieldsNotEmpty) {
                    //TODO: Create intent to dashboard with new account

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    String message = "";

                    builder.setTitle(R.string.registerAlertDialogTitle);

                    if (!validEmail) message += (getResources().getString(R.string.emailInvalid) + "\n");

                    if  (!validPassword) message += (getResources().getString(R.string.registerPasswordInvalid) + "\n");

                    if (!fieldsNotEmpty) message += ("" + getResources().getString(R.string.emptyNameOrDate));

                    builder.setMessage(message);

                    builder.setNegativeButton(R.string.alertDialogCancelButton, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();

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


    //TODO: connect to the right API URL 
    private void register(String firstName, String insertion, String lastName, String dateOfBirth, String email, String password) {

        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "https://prog4sk.herokuapp.com/api/login";

        JSONObject body = new JSONObject();
        try {
            body.put("firstName", firstName);
            body.put("insertion", insertion);
            body.put("lastName", lastName);
            body.put("dateOfBirth", dateOfBirth);
            body.put("email", email);
            body.put("password", password);
        } catch(Exception e) {
            Log.e("VOLLEY_TAG", e.toString());
        }

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY_TAG", response.toString());
                        try {
                            Token = response.getString("token");
                            Log.d("the token", Token);
                            Intent i = new Intent(getApplicationContext(), HomepageActivity.class);

                            startActivity(i);
                        } catch (JSONException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                        validCredentials.setTextColor(Color.RED);
                        validCredentials.setText(R.string.unValidCredentials);
                    }
                }
        );

        queue.add(request);
    }
}
