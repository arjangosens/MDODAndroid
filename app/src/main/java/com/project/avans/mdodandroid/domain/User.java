package com.project.avans.mdodandroid.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private String name;
    private String insertion;
    private String lastname;
    private String email;
    private String date;



    public User(String name, String insertion, String lastname, String email, String date) {
        this.name = name;
        this.insertion = insertion;
        this.lastname = lastname;
        this.email = email;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {

        return name;
    }

    public String getInsertion() {
        return insertion;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }


    public String getDate() {
        return date;
    }


}
