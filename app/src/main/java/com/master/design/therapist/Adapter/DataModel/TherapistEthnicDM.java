package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class TherapistEthnicDM {

    private ArrayList<Ethnic_details> ethnic_details;
    private String  status;

    public ArrayList<Ethnic_details> getEthnic_details() {
        return ethnic_details;
    }

    public void setEthnic_details(ArrayList<Ethnic_details> ethnic_details) {
        this.ethnic_details = ethnic_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
