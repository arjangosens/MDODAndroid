package com.project.avans.mdodandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.project.avans.mdodandroid.consumptionAdapter.ConsumptionAdapter;
import com.project.avans.mdodandroid.consumptionAdapter.ConsumptionSpecDayAdapter;
import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.ConsumptionsPerDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsumptionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ConsumptionAdapter consumptionAdapter;
    private ArrayList<ConsumptionsPerDay> consumptionsPerDayArrayList;
    private Date date;
    public final static String CONSUMPTIONSPD = "consumptionsPerDay";

    private ListView cpdListView;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        consumptionsPerDayArrayList = new ArrayList<>();
        date = Calendar.getInstance().getTime();

        // Create test data
        getUsage();



        cpdListView = (ListView) findViewById(R.id.activityConsumption_listView);
        consumptionAdapter = new ConsumptionAdapter(getLayoutInflater(), consumptionsPerDayArrayList);
        cpdListView.setAdapter(consumptionAdapter);
        cpdListView.setOnItemClickListener(this);

        registerButton = findViewById(R.id.activityConsumption_buttonRegisterConsumption);
        registerButton.setOnClickListener(this);
    }

    private void getUsage() {
        //Create test data (for now)
        ConsumptionsPerDay cpdToday = new ConsumptionsPerDay(date);
        Consumption consumption = new Consumption(date, "Alcohol", 5, "idk", 1);
        cpdToday.add(consumption);

        date = Calendar.getInstance().getTime();

        consumption = new Consumption(date, "Weed", 5, "idk2", 2);
        cpdToday.add(consumption);


        consumptionsPerDayArrayList.add(cpdToday);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), ConsumptionSpecificDayActivity.class);

        ConsumptionsPerDay consumptionsPerDay = consumptionsPerDayArrayList.get(position);
        i.putExtra(CONSUMPTIONSPD, consumptionsPerDay);

        startActivity(i);
    }

    @Override
    public void onClick(View v) {
       Intent intent = new Intent(getApplicationContext(), RegisterConsumptionActivity.class);
       startActivity(intent);
        
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
