package com.project.avans.mdodandroid.domain;

/**
 * Created by kelly on 12-6-2018.
 */

public class Message {
    private String message;
    private String date;
    private String sendBy;

    public Message(String message, String date, String sendBy) {
        this.message = message;
        this.date = date;
        this.sendBy = sendBy;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getSendBy() {
        return sendBy;
    }
}
