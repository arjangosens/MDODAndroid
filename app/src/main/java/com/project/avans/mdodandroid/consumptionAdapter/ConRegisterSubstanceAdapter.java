package com.project.avans.mdodandroid.consumptionAdapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Consumption;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.text.DateFormat;
import java.util.ArrayList;


public class ConRegisterSubstanceAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList substanceArrayList;

    public ConRegisterSubstanceAdapter(LayoutInflater inflater, ArrayList consumptionsPerDayArrayList) {
        this.inflater = inflater;
        this.substanceArrayList = consumptionsPerDayArrayList;
    }

    @Override
    public int getCount() {
        int size = substanceArrayList.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        Substance rv = (Substance) substanceArrayList.get(i);
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
            view = inflater.inflate(R.layout.activity_row_consumption_type, null);
            viewHolder = new ViewHolder();
            viewHolder.button = view.findViewById(R.id.row_consumptionType_button);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Substance rv = (Substance) substanceArrayList.get(i);

        viewHolder.button.setText(rv.getType());

        Drawable top = rv.getDrawable();
        viewHolder.button.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);

        return view;
    }

    private static class ViewHolder{
        Button button;
    }
}


