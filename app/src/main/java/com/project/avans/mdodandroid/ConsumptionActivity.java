package com.project.avans.mdodandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.avans.mdodandroid.consumptionAdapter.ConsumptionSpecDayAdapter;
import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.ConsumptionsPerDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsumptionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ConsumptionSpecDayAdapter consumptionAdapter;
    private ArrayList<ConsumptionsPerDay> consumptionsPerDayArrayList;
    private Date date;
    public final static String CONSUMPTIONSPD = "consumptionsPerDay";

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

        consumption = new Consumption(date, "Weed", 5, "idk2");
        cpdToday.add(consumption);


        consumptionsPerDayArrayList.add(cpdToday);



        cpdListView = (ListView) findViewById(R.id.activityConsumption_listView);
        consumptionAdapter = new ConsumptionSpecDayAdapter(getLayoutInflater(), consumptionsPerDayArrayList);
        cpdListView.setAdapter(consumptionAdapter);
        cpdListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), ConsumptionSpecificDayActivity.class);

        ConsumptionsPerDay consumptionsPerDay = consumptionsPerDayArrayList.get(position);
        i.putExtra(CONSUMPTIONSPD, consumptionsPerDay);

        startActivity(i);
    }
}
