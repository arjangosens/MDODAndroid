package com.project.avans.mdodandroid.domain;

/**
 * Created by kelly on 12-6-2018.
 */

public class Message {
    private String message;
    private String date;
    private String psychologist;

    public Message(String message, String date, String psychologist) {
        this.message = message;
        this.date = date;
        this.psychologist = psychologist;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getPsychologist() {
        return psychologist;
    }
}
