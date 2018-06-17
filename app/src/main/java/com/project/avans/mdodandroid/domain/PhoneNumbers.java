package com.project.avans.mdodandroid.domain;

public class PhoneNumbers {

    private String Institute;
    private String Doctor;
    private String Buddy;
    private String Emergency;


    public PhoneNumbers(String institute, String doctor, String buddy, String emergency) {
        Institute = institute;
        Doctor = doctor;
        Buddy = buddy;
        Emergency = emergency;
    }

    public String getPhoneInstitute() {
        return Institute;
    }

    public String getPhoneDoctor() {
        return Doctor;
    }

    public String getPhoneBuddy() {
        return Buddy;
    }

    public String getPhoneEmergency() {
        return Emergency;
    }
}
