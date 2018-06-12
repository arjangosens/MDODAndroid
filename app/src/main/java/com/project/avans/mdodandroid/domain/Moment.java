package com.project.avans.mdodandroid.domain;

public class Moment {
    private String name;
    private int lust;
    private String description;
    private String date;
    private String prevention;

    public Moment(String name, String date, String description, int lust, String prevention) {
        this.name = name;
        this.lust = lust;
        this.description = description;
        this.date = date;
        this.prevention = prevention;
    }

    public String getName() {
        return name;
    }

    public int getLust() {
        return lust;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getPrevention() {
        return prevention;
    }
}
