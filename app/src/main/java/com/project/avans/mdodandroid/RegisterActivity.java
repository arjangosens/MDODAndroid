package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerButton;
    private Button datePickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.activityRegister_buttonCreateAccount);
        datePickerButton = findViewById(R.id.activityRegister_buttonDateOfBirth);

        registerButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activityRegister_buttonDateOfBirth:
                Log.i("RegisterActivity", "onClick of datePickerButton called");

                //TODO: Create a DatePicker

                break;

            case R.id.activityRegister_buttonCreateAccount:
                Log.i("RegisterActivity", "onClick of registerButton called");
                //TODO: Create intent to dashboard with new account

                //TODO: Pass data through API and check if data is correct
                break;
        }
    }
}
