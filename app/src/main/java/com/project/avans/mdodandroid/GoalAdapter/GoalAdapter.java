package com.project.avans.mdodandroid.GoalAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Goal;

import java.util.ArrayList;


public class GoalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList goalArray;

    public GoalAdapter(LayoutInflater inflater, ArrayList goalArray) {
        this.inflater = inflater;
        this.goalArray = goalArray;
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
            view = inflater.inflate(R.layout.activity_row_goal, null);
            viewHolder = new GoalAdapter.ViewHolder();
            viewHolder.goal = view.findViewById(R.id.goalRow);
            viewHolder.status = view.findViewById(R.id.checkBox_goal);
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
        Goal rv = (Goal) goalArray.get(i);
        viewHolder.goal.setText(rv.Goal());

        if(rv.getStatus().equals("0")){
            viewHolder.status.setChecked(false);
        } else if (rv.getStatus().equals("1")){
            viewHolder.status.setChecked(true);
        }
        
        return view;
    }

    private static class ViewHolder{
        TextView goal;
        CheckBox status;
    }
}
