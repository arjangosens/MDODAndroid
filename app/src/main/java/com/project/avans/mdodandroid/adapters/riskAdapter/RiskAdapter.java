package com.project.avans.mdodandroid.adapters.riskAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.domain.Risk;

import java.util.ArrayList;



    public class RiskAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList RiskArray;

        public RiskAdapter(LayoutInflater inflater, ArrayList RiskArray) {
            this.inflater = inflater;
            this.RiskArray = RiskArray;
        }

        @Override
        public int getCount() {
            int size = RiskArray.size();
            Log.i("getCount()", "=" + size);
            return size;
        }

        @Override
        public Object getItem(int i) {
            Risk rv = (Risk) RiskArray.get(i);
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
                view = inflater.inflate(R.layout.row_risk, null);
                viewHolder = new ViewHolder();
                viewHolder.Risk = view.findViewById(R.id.RiskRow);
                TextView risk = (TextView)view.findViewById(R.id.textView_row);
                risk.setText(R.string.risk);

                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Risk rv = (Risk) RiskArray.get(i);
            viewHolder.Risk.setText(rv.Risk());
            return view;
        }

        private static class ViewHolder{
            TextView Risk;
        }
    }


