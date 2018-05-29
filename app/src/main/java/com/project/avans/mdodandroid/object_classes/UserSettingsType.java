package com.project.avans.mdodandroid.object_classes;

public class UserSettingsType {
    private String type;
    private String value;

    public UserSettingsType(String type) {
        this.type = type;
        value = "";
    }

    public UserSettingsType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.isEmpty()) {

            return type;

        } else {

            return type + ": " + value;
        }
    }
}
