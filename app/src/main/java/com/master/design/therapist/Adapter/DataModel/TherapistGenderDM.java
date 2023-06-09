package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class TherapistGenderDM {

    private ArrayList<Gender_details> gender_details;
    private String  status;

    public ArrayList<Gender_details> getGender_details() {
        return gender_details;
    }

    public void setGender_details(ArrayList<Gender_details> gender_details) {
        this.gender_details = gender_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
