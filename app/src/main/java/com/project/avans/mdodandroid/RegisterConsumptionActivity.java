package com.project.avans.mdodandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.project.avans.mdodandroid.consumptionAdapter.ConRegisterSubstanceAdapter;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.util.ArrayList;

public class RegisterConsumptionActivity extends AppCompatActivity {
    private ListView substanceListView;
    private ArrayList<Substance> substances;
    private ConRegisterSubstanceAdapter conRegisterSubstanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumption);

        substances = new ArrayList<>();
        substances.add(new Substance("Alcohol", getResources().getDrawable(R.drawable.wine), "ml"));
        substances.add(new Substance("Alcohol2", getResources().getDrawable(R.drawable.wine), "ml"));

        substanceListView = (ListView) findViewById(R.id.act_registerConsumption_listViewTypes);
        conRegisterSubstanceAdapter = new ConRegisterSubstanceAdapter(getLayoutInflater(), substances);
        substanceListView.setAdapter(conRegisterSubstanceAdapter);


    }
}
