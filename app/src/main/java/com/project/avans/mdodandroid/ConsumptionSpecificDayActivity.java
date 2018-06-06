package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.ConsumptionsPerDay;

import java.util.ArrayList;

public class ConsumptionSpecificDayActivity extends AppCompatActivity {
    private ArrayList<Consumption> consumptions;
    private ConsumptionsPerDay consumptionsPerDay;
    private final static String TAG = "ConSpecDayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_specific_day);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            consumptionsPerDay = (ConsumptionsPerDay) extras.get(ConsumptionActivity.CONSUMPTIONSPD);

            if (consumptionsPerDay != null) {
                consumptions = consumptionsPerDay.getConsumptions();
            }

            Log.i(TAG, "Size of ArrayList: " + consumptions.size());
        }
    }
}
