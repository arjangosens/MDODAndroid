package com.project.avans.mdodandroid.adapters.goalAdapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.project.avans.mdodandroid.domain.Goal;


public class onGoalClick extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Context context;
    private LayoutInflater inflater;
    private OnAlertBoxAvailable listener;

    public onGoalClick(Context context, LayoutInflater inflater, OnAlertBoxAvailable listener) {
        this.context = context;
        this.inflater = inflater;
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Goal rv = (Goal) adapterView.getItemAtPosition(i);
//            Intent intent = new Intent(context, MainActivity.class); // TODO change to edit page
//            intent.putExtra("Goal", rv);
//            context.startActivity(intent);
        listener.onAlertBoxAvailable(rv);
    }

}
