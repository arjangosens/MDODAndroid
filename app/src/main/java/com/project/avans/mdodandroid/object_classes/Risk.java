package com.project.avans.mdodandroid.object_classes;

import java.io.Serializable;

public class Risk implements Serializable {


    private String RiskID;
    private String Risk;


    public Risk(String RiskID, String Risk) {
        this.RiskID = RiskID;
        this.Risk = Risk;

    }

    public String getRiskID() {
        return RiskID;
    }

    public String Risk() {
        return Risk;
    }


    @Override
    public String toString() {
        return "Risk{" +
                "RiskID='" + RiskID + '\'' +
                ", Risk='" + Risk + '\'' +
                '}';
    }
}

