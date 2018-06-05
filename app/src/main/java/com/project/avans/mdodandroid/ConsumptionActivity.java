package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.project.avans.mdodandroid.consumptionAdapter.ConsumptionAdapter;
import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.ConsumptionsPerDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsumptionActivity extends AppCompatActivity {
    private ConsumptionAdapter consumptionAdapter;
    private ArrayList<ConsumptionsPerDay> consumptionsPerDayArrayList;
    private Date date;

    private ListView cpdListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        consumptionsPerDayArrayList = new ArrayList<>();
        date = Calendar.getInstance().getTime();

        // Create test data
        ConsumptionsPerDay cpdToday = new ConsumptionsPerDay(date);
        Consumption consumption = new Consumption(date, "Alcohol", 5, "idk");

        cpdToday.add(consumption);


        consumptionsPerDayArrayList.add(cpdToday);



        cpdListView = (ListView) findViewById(R.id.activityConsumption_listView);
        consumptionAdapter = new ConsumptionAdapter(getLayoutInflater(), consumptionsPerDayArrayList);
        cpdListView.setAdapter(consumptionAdapter);
    }
}