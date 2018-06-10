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

import java.text.DateFormat;
import java.util.ArrayList;


public class ConsumptionSpecDayAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList consumptionsArrayList;

    public ConsumptionSpecDayAdapter(LayoutInflater inflater, ArrayList consumptionsArrayList) {
        this.inflater = inflater;
        this.consumptionsArrayList = consumptionsArrayList;
    }

    @Override
    public int getCount() {
        int size = consumptionsArrayList.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        ConsumptionsPerDay rv = (ConsumptionsPerDay) consumptionsArrayList.get(i);
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
            view = inflater.inflate(R.layout.activity_row_consumptionspecifcday, null);
            viewHolder = new ViewHolder();
            viewHolder.type = view.findViewById(R.id.row_consumptionSpecificDay_textViewType);
            viewHolder.timestamp = view.findViewById(R.id.row_consumptionSpecificDay_textViewTimeStamp);
            viewHolder.amount = view.findViewById(R.id.row_consumptionSpecificDay_textViewAmount);
            viewHolder.measurement = view.findViewById(R.id.row_consumptionSpecificDay_textViewMeasurement);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Consumption rv = (Consumption) consumptionsArrayList.get(i);

        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(rv.getDate());

        viewHolder.timestamp.setText(time);

        String measurement = "";

        if (!rv.getMeasurement().isEmpty() && !rv.getMeasurement().equals("null")) {
            measurement = rv.getMeasurement();
        }

        String type = rv.getType();


        if (type.isEmpty()) {
            type = "Nothing";
        }

        viewHolder.type.setText(type);
        viewHolder.measurement.setText(measurement);

        String amount = String.valueOf(rv.getAmount());
        viewHolder.amount.setText(amount);

        return view;
    }

    private static class ViewHolder{
        TextView type;
        TextView timestamp;
        TextView amount;
        TextView measurement;
    }
}


