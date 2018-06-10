package com.project.avans.mdodandroid.object_classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsumptionsPerDay implements Serializable{
    final String TAG = "ConsumptionsPerDay";
    private Date date;
    private ArrayList<Consumption> consumptions;
    private String dateFormatString;

    public ConsumptionsPerDay(Date date) {
        this.date = date;
        dateFormatString = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        consumptions = new ArrayList<>();
    }

    public void add(Consumption consumption) {
        String consumptionDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(consumption.getDate());

        if (consumptionDateString.equals(dateFormatString)) {

            consumptions.add(consumption);
            Log.i(TAG, "consumption added");

        } else {
            Log.i(TAG, "consumption NOT added, date does NOT match!");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(ArrayList<Consumption> consumptions) {
        this.consumptions = consumptions;
    }
}
