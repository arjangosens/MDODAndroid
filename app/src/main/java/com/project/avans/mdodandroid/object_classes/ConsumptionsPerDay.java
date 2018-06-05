package com.project.avans.mdodandroid.object_classes;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ConsumptionsPerDay {
    private GregorianCalendar date;
    private ArrayList<Consumption> consumptions;

    public ConsumptionsPerDay(GregorianCalendar date) {
        this.date = date;
        consumptions = new ArrayList<>();
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public ArrayList<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(ArrayList<Consumption> consumptions) {
        this.consumptions = consumptions;
    }
}
