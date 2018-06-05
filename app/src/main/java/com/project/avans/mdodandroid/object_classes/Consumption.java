package com.project.avans.mdodandroid.object_classes;

import java.util.GregorianCalendar;

public class Consumption {
    private GregorianCalendar timestamp;
    private String type;
    private double amount;
    private String description;

    public Consumption(GregorianCalendar timestamp, String type, double amount, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Consumption(GregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }

    public GregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(GregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
