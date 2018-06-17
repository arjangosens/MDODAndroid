package com.project.avans.mdodandroid.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Consumption implements Serializable{
    private Date date;
    private String type;
    private Integer amount;
    private Integer feeling;
    private String location;
    private String cause;
    private String measurement;

    public Consumption(Date date, String type, Integer amount, String location, String cause, Integer feeling, String measurement) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.location = location;
        this.cause = cause;
        this.feeling = feeling;
        this.measurement = measurement;
    }

    public Consumption(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFeeling() {
        return feeling;
    }

    public void setFeeling(Integer feeling) {
        this.feeling = feeling;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
