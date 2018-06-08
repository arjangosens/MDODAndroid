package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.project.avans.mdodandroid.consumptionAdapter.ConRegSubstanceAdapter;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.util.ArrayList;

public class RegisterConsumptionActivity extends AppCompatActivity implements ConRegSubstanceAdapter.OnItemClickListener {
    private RecyclerView substanceRv;
    private ArrayList<Substance> substances;
    private ConRegSubstanceAdapter adapter;
    private static final String TAG = "RegConsumptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumption);

        substances = new ArrayList<>();
        substances.add(new Substance("Alcohol", getResources().getDrawable(R.drawable.wine), "ml"));
        substances.add(new Substance("Alcohol2", getResources().getDrawable(R.drawable.wine), "ml"));

        substanceRv = (RecyclerView) findViewById(R.id.act_registerConsumption_RecyclerViewSubstances);

//        adapter = new ConRegSubstanceAdapter(substances);
//        adapter.setOnItemClickListener(this);
//        substanceRv.setAdapter(adapter);

        substanceRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.adapter = new ConRegSubstanceAdapter(substances, new ConRegSubstanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(TAG, "onItemClick(" + position + ") called");
            }
        });
        substanceRv.setAdapter(this.adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        substanceRv.setLayoutManager(llm);

    }

    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "OnItemClick() called on pos " + position);
    }
}
