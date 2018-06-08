package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.consumptionAdapter.ConRegSubstanceAdapter;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.util.ArrayList;

public class RegisterConsumptionActivity extends AppCompatActivity implements ConRegSubstanceAdapter.OnItemClickListener {
    private RecyclerView substanceRv;

    private TextView typetextView;

    private ArrayList<Substance> substances;
    private ConRegSubstanceAdapter adapter;
    private static final String TAG = "RegConsumptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumption);

        substances = new ArrayList<>();
        initTypes();

        substanceRv = (RecyclerView) findViewById(R.id.act_registerConsumption_RecyclerViewSubstances);
        typetextView = (TextView) findViewById(R.id.act_registerConsumption_textViewSelectionValue);

//        adapter = new ConRegSubstanceAdapter(substances);
//        adapter.setOnItemClickListener(this);
//        substanceRv.setAdapter(adapter);

        substanceRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.adapter = new ConRegSubstanceAdapter(substances, new ConRegSubstanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(TAG, "onItemClick(" + position + ") called");

                Substance substance = substances.get(position);

                typetextView.setText(substance.getType());
            }
        });
        substanceRv.setAdapter(this.adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        substanceRv.setLayoutManager(llm);

    }

    @Override
    public void onItemClick(int position) {
//        substanceRv.getRecycledViewPool().clear();
        Log.i(TAG, "OnItemClick() called on pos " + position);
    }

    private void initTypes() {
        substances.add(new Substance("Nothing", getResources().getDrawable(R.drawable.like), ""));
        substances.add(new Substance("Alcohol", getResources().getDrawable(R.drawable.wine), "ml"));
        substances.add(new Substance("Weed", getResources().getDrawable(R.drawable.marijuana), "g"));
        substances.add(new Substance("GHB", getResources().getDrawable(R.drawable.ghb), "qwerty"));
        substances.add(new Substance("LSD", getResources().getDrawable(R.drawable.lsd), "mg"));
        substances.add(new Substance("Cocaine", getResources().getDrawable(R.drawable.cocaine), "lines"));
        substances.add(new Substance("Other", getResources().getDrawable(R.drawable.question), ""));
    }
}
