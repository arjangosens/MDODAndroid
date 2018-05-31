package com.project.avans.mdodandroid.adapters.RiskAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.project.avans.mdodandroid.MainActivity;
import com.project.avans.mdodandroid.object_classes.Risk;

public class onRiskClick implements AdapterView.OnItemClickListener{
        private Context context;

        public onRiskClick(Context context) {
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Risk rv = (Risk) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(context, MainActivity.class); // TODO change to edit page
            intent.putExtra("Risk", rv);
            context.startActivity(intent);
        }
    }

