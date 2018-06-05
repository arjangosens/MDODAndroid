package com.project.avans.mdodandroid.momentAdapter;

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

import com.project.avans.mdodandroid.GoalAdapter.GoalAdapter;
import com.project.avans.mdodandroid.GoalAdapter.OnAlertBoxAvailable;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.object_classes.Goal;
import com.project.avans.mdodandroid.object_classes.Moment;

import org.json.JSONObject;

import java.util.ArrayList;

public class MomentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList momentArray;
    private Context context;

    public MomentAdapter(Context context, LayoutInflater inflater, ArrayList momentArray) {
        this.inflater = inflater;
        this.momentArray = momentArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        int size = momentArray.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        Goal rv = (Goal) momentArray.get(i);
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
        MomentAdapter.ViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.activity_row_goal, null);
            viewHolder = new MomentAdapter.ViewHolder();

            view.setTag(viewHolder);
        }else {
            viewHolder = (MomentAdapter.ViewHolder) view.getTag();
        }

        final Moment moment = (Moment) momentArray.get(i);

        return view;
    }

    private static class ViewHolder{
        TextView moment;
    }
}
