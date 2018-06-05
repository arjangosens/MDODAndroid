package com.project.avans.mdodandroid.object_classes;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class ConsumptionsPerDay implements Serializable{
    final String TAG = "ConsumptionsPerDay";
    private Calendar date;
    private ArrayList<Consumption> consumptions;

    public ConsumptionsPerDay(Calendar date) {
        this.date = date;
        consumptions = new ArrayList<>();
    }

    public void add(Consumption consumption) {
        Calendar consumptionDate = consumption.getTimestamp();

        if (consumptionDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                && consumptionDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                && consumptionDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {

            consumptions.add(consumption);
            Log.i(TAG, "consumption added");

        } else {
            Log.i(TAG, "consumption NOT added, date does NOT match!");
        }
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(ArrayList<Consumption> consumptions) {
        this.consumptions = consumptions;
    }
}
