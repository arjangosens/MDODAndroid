package com.project.avans.mdodandroid.object_classes;

public class Substance {
    private String id;
    private String type;

    public Substance(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
