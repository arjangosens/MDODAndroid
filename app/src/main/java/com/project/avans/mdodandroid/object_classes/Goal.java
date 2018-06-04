package com.project.avans.mdodandroid.object_classes;

import java.io.Serializable;

public class Goal implements Serializable {
    private String goalID;
    private String goal;
    private String status;

    public Goal(String goalID, String goal, String status) {
        this.goalID = goalID;
        this.goal = goal;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getGoalID() {
        return goalID;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", status=" + status +
                '}';
    }
}
