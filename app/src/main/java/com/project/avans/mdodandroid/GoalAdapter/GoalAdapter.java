package com.project.avans.mdodandroid.GoalAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            view = inflater.inflate(R.layout.activity_row_risk, null);
            viewHolder = new GoalAdapter.ViewHolder();
            viewHolder.goal = view.findViewById(R.id.RiskRow);
            view.setTag(viewHolder);
        }else {
            viewHolder = (GoalAdapter.ViewHolder) view.getTag();
        }
        Goal rv = (Goal) goalArray.get(i);
        viewHolder.goal.setText(rv.Goal());
        return view;
    }

    private static class ViewHolder{
        TextView goal;
    }
}
