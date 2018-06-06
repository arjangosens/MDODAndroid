package com.project.avans.mdodandroid.object_classes;

public class Moment {
    private String name;
    private int lust;
    private String description;
    private String date;

    public Moment(String name, String date, String description, int lust) {
        this.name = name;
        this.lust = lust;
        this.description = description;
        this.date = date;

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

}
