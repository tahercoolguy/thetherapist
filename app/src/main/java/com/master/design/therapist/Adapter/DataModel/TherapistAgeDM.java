package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class TherapistAgeDM {

    private ArrayList<Age_details> age_details;
    private String status;

    public ArrayList<Age_details> getAge_details() {
        return age_details;
    }

    public void setAge_details(ArrayList<Age_details> age_details) {
        this.age_details = age_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
