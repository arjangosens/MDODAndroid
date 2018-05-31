package com.project.avans.mdodandroid.object_classes;

import java.io.Serializable;

public class Goal implements Serializable {
    private String goal;


    public Goal(String Goal) {
        this.goal = Goal;

    }

    public String Goal() {
        return goal;
    }


    @Override
    public String toString() {
        return "Risk{" +
                ", Risk='" + goal + '\'' +
                '}';
    }
}
