package com.project.avans.mdodandroid.userSettingsAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Risk;
import com.project.avans.mdodandroid.object_classes.UserSettingsType;

import java.util.ArrayList;


public class UserSettingsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList userSettingsArray;

    public UserSettingsAdapter(LayoutInflater inflater, ArrayList userSettingsArray) {
        this.inflater = inflater;
        this.userSettingsArray = userSettingsArray;
    }

    @Override
    public int getCount() {
        int size = userSettingsArray.size();
        Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int i) {
        UserSettingsType rv = (UserSettingsType) userSettingsArray.get(i);
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
            view = inflater.inflate(R.layout.activity_row_usersettings, null);
            viewHolder = new ViewHolder();
            viewHolder.type = view.findViewById(R.id.row_userSettings_textViewType);
            viewHolder.value = view.findViewById(R.id.row_userSettings_textViewValue);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        UserSettingsType rv = (UserSettingsType) userSettingsArray.get(i);
        viewHolder.type.setText(rv.getType());

        String value = rv.getValue();
        if (value.equals("")) {
            value = view.getResources().getString(R.string.fieldNotFilledIn);
        }

        viewHolder.value.setText(value);
        return view;
    }

    private static class ViewHolder{
        TextView type;
        TextView value;
    }
}


