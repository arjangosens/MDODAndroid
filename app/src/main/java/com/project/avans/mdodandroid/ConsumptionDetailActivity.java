package com.project.avans.mdodandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    private ImageView feelingImg;

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
        feelingImg = findViewById(R.id.act_consumptionDetail_imageViewType);

        typeTextView.setText(consumption.getType());
        amountValueTextView.setText(String.valueOf(consumption.getAmount()));
        timestampTextView.setText(date + " | " + time);
        descriptionTextView.setText(consumption.getDescription());


    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ConsumptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
