package com.project.avans.mdodandroid.adapters.messageAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.adapters.momentAdapter.MomentAdapter;
import com.project.avans.mdodandroid.domain.Message;

import java.util.ArrayList;

/**
 * Created by kelly on 12-6-2018.
 */

public class MessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList messageArray;
    private Context context;

    public MessageAdapter(LayoutInflater inflater, ArrayList messageArray, Context context) {
        this.inflater = inflater;
        this.messageArray = messageArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messageArray.size();
    }

    @Override
    public Object getItem(int i) {
        Message rv = (Message) messageArray.get(i);
        Log.i("getItem()","=" + rv);
        return rv;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.row_message, null);
            viewHolder = new ViewHolder();

            viewHolder.date = view.findViewById(R.id.textView_messages_date);
            viewHolder.message = view.findViewById(R.id.textView_message_view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Log.i("TEST5: ", messageArray.toString());
        final Message message = (Message) messageArray.get(i);

        viewHolder.date.setText(message.getDate());
        viewHolder.message.setText(message.getMessage());

        return view;
    }

    private static class ViewHolder{
        TextView date;
        TextView message;
    }
}
