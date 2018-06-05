package com.project.avans.mdodandroid.consumptionAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.ConsumptionsPerDay;
import com.project.avans.mdodandroid.object_classes.UserSettingsType;

import java.util.ArrayList;


public class ConsumptionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList consumptionsPerDayArrayList;

    public ConsumptionAdapter(LayoutInflater inflater, ArrayList consumptionsPerDayArrayList) {
        this.inflater = inflater;
        this.consumptionsPerDayArrayList = consumptionsPerDayArrayList;
    }

    @Override
    public int getCount() {
        int size = consumptionsPerDayArrayList.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        ConsumptionsPerDay rv = (ConsumptionsPerDay) consumptionsPerDayArrayList.get(i);
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
        ViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.activity_row_consumption, null);
            viewHolder = new ViewHolder();
            viewHolder.types = view.findViewById(R.id.row_consumption_textViewTypes);
            viewHolder.date = view.findViewById(R.id.row_consumption_textViewDate);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ConsumptionsPerDay rv = (ConsumptionsPerDay) consumptionsPerDayArrayList.get(i);
        viewHolder.date.setText(rv.getDate().toString());

        String types = "";

        for (Consumption consumption : rv.getConsumptions()) {
            types = (types + "" + consumption.getType());
        }

        if (types.isEmpty()) {
         types = "Nothing";
        }

        viewHolder.types.setText(types);
        return view;
    }

    private static class ViewHolder{
        TextView types;
        TextView date;
    }
}


