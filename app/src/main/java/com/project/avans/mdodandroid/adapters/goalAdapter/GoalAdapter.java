package com.project.avans.mdodandroid.adapters.goalAdapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.Goal;

import org.json.JSONObject;

import java.util.ArrayList;


public class GoalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList goalArray;
    private Context context;
    private OnAlertBoxAvailable listener;

    public GoalAdapter(Context context, OnAlertBoxAvailable listener, LayoutInflater inflater, ArrayList goalArray) {
        this.inflater = inflater;
        this.goalArray = goalArray;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        int size = goalArray.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        Goal rv = (Goal) goalArray.get(i);
        Log.i("getItem()","=" + rv);
        return rv;
    }

    @Override
    public long getItemId(int i) {
        Log.i("getItemId()", "=" + i);
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GoalAdapter.ViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.row_goal, null);
            viewHolder = new GoalAdapter.ViewHolder();
            viewHolder.goal = view.findViewById(R.id.goalRow);
            viewHolder.status = view.findViewById(R.id.checkBox_goal);
            viewHolder.goalLayout = view.findViewById(R.id.constraintLayout_goal);
            view.setTag(viewHolder);
        }else {
            viewHolder = (GoalAdapter.ViewHolder) view.getTag();
        }

//        CheckBox status = findViewById(R.id.checkBox_goal);
//        if(goal.getStatus().equals("0")){
//            status.setChecked(false);
//        } else if (goal.getStatus().equals("1")){
//            status.setChecked(true);
//        }
        final Goal rv = (Goal) goalArray.get(i);
        viewHolder.goal.setText(rv.Goal());

        if(rv.getStatus().equals("0")){
            viewHolder.status.setChecked(false);
        } else if (rv.getStatus().equals("1")){
            viewHolder.status.setChecked(true);
        }

        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rv.setStatus("1");
                    NetworkManager.getInstance().putStatusGoal(rv.getGoalID(), "1", new VolleyListener<JSONObject>() {
                        @Override
                        public void getResult(JSONObject object) {

                        }
                    });
                } else {
                    rv.setStatus("0");
                    NetworkManager.getInstance().putStatusGoal(rv.getGoalID(), "0", new VolleyListener<JSONObject>() {
                        @Override
                        public void getResult(JSONObject object) {

                        }
                    });
                }
            }
        });

        viewHolder.goalLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onAlertBoxAvailable(rv);
            }
        });

        return view;
    }

    private static class ViewHolder{
        TextView goal;
        CheckBox status;
        ConstraintLayout goalLayout;
    }
}
