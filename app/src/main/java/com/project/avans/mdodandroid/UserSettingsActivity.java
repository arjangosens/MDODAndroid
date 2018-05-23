package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserSettingsActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView insertion;
    private TextView lastName;
    private TextView dateOfBirth;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //TODO: connect the Textviews to the userdata

        firstName = (TextView) findViewById(R.id.textView_data_firstname);
        insertion = (TextView) findViewById(R.id.textView_data_insertion);
        lastName = (TextView) findViewById(R.id.textView_data_lastname);
        dateOfBirth = (TextView) findViewById(R.id.textView_data_date);
        email = (TextView) findViewById(R.id.textView_data_email);

    }
}
