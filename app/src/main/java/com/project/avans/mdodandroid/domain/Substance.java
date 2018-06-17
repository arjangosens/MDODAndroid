package com.project.avans.mdodandroid.domain;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Substance {
    private String id;
    private String type;
    private Drawable drawable;
    private String measurement;
    private ArrayList<Integer> amountOptions;

    public Substance(String id, String type) {
        this.id = id;
        this.type = type;
        amountOptions = new ArrayList<>();
    }

    public Substance(String type, Drawable drawable, String measurement) {
        this.type = type;
        this.drawable = drawable;
        this.measurement = measurement;
    }

    public void addAmountOption(int amount) {
        amountOptions.add(amount);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public ArrayList<Integer> getAmountOptions() {
        return amountOptions;
    }

    public void setAmountOptions(ArrayList<Integer> amountOptions) {
        this.amountOptions = amountOptions;
    }
}
