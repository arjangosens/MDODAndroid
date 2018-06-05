package com.project.avans.mdodandroid.object_classes;

public class Moment {
    private String id;
    private String lust;
    private String description;
    private String date;
    private String type;
    private String typeId;

    public Moment(String id, String lust, String description, String date, String type, String typeId) {
        this.id = id;
        this.lust = lust;
        this.description = description;
        this.date = date;
        this.type = type;
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public String getLust() {
        return lust;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getTypeId() {
        return typeId;
    }
}
