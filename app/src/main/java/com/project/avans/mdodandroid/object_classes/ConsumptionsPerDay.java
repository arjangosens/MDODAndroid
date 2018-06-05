package com.project.avans.mdodandroid.object_classes;

import java.util.ArrayList;
import java.util.Calendar;

public class ConsumptionsPerDay {
    private Calendar date;
    private ArrayList<Consumption> consumptions;

    public ConsumptionsPerDay(Calendar date) {
        this.date = date;
        consumptions = new ArrayList<>();
    }

    public void add(Consumption consumption) {

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
