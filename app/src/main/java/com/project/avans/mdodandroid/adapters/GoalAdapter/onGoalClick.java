package com.project.avans.mdodandroid.adapters.GoalAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.project.avans.mdodandroid.MyPersonalGoalsActivity;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Goal;


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
