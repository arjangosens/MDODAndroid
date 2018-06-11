package com.project.avans.mdodandroid.activities.homepageActivies.consumption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.consumption.ConsumptionActivity;
import com.project.avans.mdodandroid.activities.homepageActivies.consumption.ConsumptionDetailActivity;
import com.project.avans.mdodandroid.adapters.consumptionAdapter.ConsumptionSpecDayAdapter;
import com.project.avans.mdodandroid.domain.Consumption;
import com.project.avans.mdodandroid.domain.ConsumptionsPerDay;

import java.text.DateFormat;
import java.util.ArrayList;

public class ConsumptionSpecificDayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Consumption> consumptions;
    private ConsumptionsPerDay consumptionsPerDay;
    private final static String TAG = "ConSpecDayActivity";
    public final static  String CONSUMPTION = "consumption";

    private ListView consumptionsListView;
    private ConsumptionSpecDayAdapter consumptionSpecDayAdapter;
    private TextView headerTextView;

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

        headerTextView = findViewById(R.id.activityConsumptionSpecificDay_textViewHeader);
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(consumptionsPerDay.getDate());
        headerTextView.setText(getResources().getString(R.string.consumptionOnDate, date));

        consumptionsListView = (ListView) findViewById(R.id.activityConsumptionSpecificDay_listView);
        consumptionSpecDayAdapter = new ConsumptionSpecDayAdapter(getLayoutInflater(), consumptions);
        consumptionsListView.setAdapter(consumptionSpecDayAdapter);
        consumptionsListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), ConsumptionDetailActivity.class);

        Consumption consumption = consumptions.get(position);
        i.putExtra(CONSUMPTION, consumption);

        startActivity(i);

    }
}
