package com.project.avans.mdodandroid.object_classes;

import java.io.Serializable;

public class Goal implements Serializable {
    private String goalID;
    private String goal;

    public Goal(String goalID, String goal) {
        this.goalID = goalID;
        this.goal = goal;

    }

    public String getGoalID() {
        return goalID;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String Goal() {
        return goal;
    }


    @Override
    public String toString() {
        return "Goal{" +
                "goalID='" + goalID + '\'' +
                ", goal='" + goal + '\'' +
                '}';
    }
}
