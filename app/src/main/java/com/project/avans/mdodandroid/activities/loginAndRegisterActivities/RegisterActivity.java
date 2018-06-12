package com.project.avans.mdodandroid.activities.loginAndRegisterActivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.applicationLogic.ValueChecker;
import com.project.avans.mdodandroid.domain.User;

import org.json.JSONObject;

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
    private Context context = this;
    CharSequence text = "Account aangemaakt";
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //removes the title from the title bar in the registerActivity
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.registerActivityHeader));

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

                String username = String.valueOf(firstNameEditText.getText());
                String insert = String.valueOf(insertionEditText.getText());
                String lastname = String.valueOf(lastNameEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                String confirmPassword = String.valueOf(confirmPasswordEditText.getText());
                email = String.valueOf(emailEditText.getText());

                boolean validPassword = ValueChecker.checkPassword(password, confirmPassword);
                boolean fieldsNotEmpty = checkIfEmptyFields();
                boolean validEmail = ValueChecker.checkEmail(email);

                if (validPassword && validEmail && fieldsNotEmpty) {
                    //intent to login
                    if (insert.isEmpty()) {
                        insert = "";
                    }
                    User user = new User(username, insert, lastname, email, dateOfBirth);
                    register(user, password);
                    Log.i("user", "user: " + user.getDate() + " " + user.getEmail());
                    Log.i("infix", "infix: " + user.getInsertion() + "I");


                } else {
                    //parameter checks
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    String message = "";

                    builder.setTitle(R.string.registerAlertDialogTitle);

                    if (!validEmail)
                        message += (getResources().getString(R.string.emailInvalid) + "\n");

                    if (!validPassword)
                        message += (getResources().getString(R.string.registerPasswordInvalid) + "\n");

                    if (!fieldsNotEmpty)
                        message += ("" + getResources().getString(R.string.emptyNameOrDate));

                    builder.setMessage(message);

                    builder.setNegativeButton(R.string.alertDialogCancelButton, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();

                }


                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;

        dateOfBirth = (year + "-" + month + "-" + dayOfMonth);
        Log.i("RegisterActivity", "onDateSet called, date: " + dateOfBirth);
        datePickerButton.setText(dateOfBirth);
        //TODO future date check?
    }


    //connect to the right API URL and gives body parameters
    private void register(User user, String password)

    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "https://mdod.herokuapp.com/api/register/client";

        JSONObject body = new JSONObject();
        try {
            body.put("firstname", user.getName());
            body.put("infix", user.getInsertion());
            body.put("lastname", user.getLastname());
            body.put("dob", user.getDate());
            body.put("email", user.getEmail());
            body.put("password", password);
        } catch (Exception e) {
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
                        Log.d("VOLLEY_TAG response", response.toString());
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error.networkResponse.statusCode == 420){
                            validCredentials.setTextColor(Color.RED);
                            validCredentials.setText(R.string.invalidEmail);
                        }
                        else{
                            Log.d("VOLLEY_TAG", error.toString());
                            validCredentials.setTextColor(Color.RED);
                            validCredentials.setText(R.string.inValidCredentials);
                        }

                    }
                }
        );

        queue.add(request);
    }
}
