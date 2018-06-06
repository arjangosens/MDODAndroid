package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.project.avans.mdodandroid.object_classes.Consumption;

import java.text.DateFormat;

public class ConsumptionDetailActivity extends AppCompatActivity {
    private Consumption consumption;

    private TextView typeTextView;
    private TextView timestampTextView;
    private TextView amountValueTextView;
    private TextView amountTypeTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_detail);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            consumption = (Consumption) extras.get(ConsumptionSpecificDayActivity.CONSUMPTION);
        }

        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(consumption.getDate());
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(consumption.getDate());

        typeTextView = findViewById(R.id.activityConsumptionDetail_textViewHeader);
        timestampTextView = findViewById(R.id.act_consumptionDetail_textViewTimeStampField);
        amountValueTextView = findViewById(R.id.act_consumptionDetail_textViewAmountValueField);
        amountTypeTextView = findViewById(R.id.act_consumptionDetail_textViewAmountTypeField);
        descriptionTextView = findViewById(R.id.act_consumptionDetail_textViewDescField);

        typeTextView.setText(consumption.getType());
        amountValueTextView.setText(String.valueOf(consumption.getAmount()));
        timestampTextView.setText(date + " | " + time);
        descriptionTextView.setText(consumption.getDescription());


    }
}
