package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class TherapistInterestDM {

    private ArrayList<Interest_details> interest_details;
    private String status;


    public ArrayList<Interest_details> getInterest_details() {
        return interest_details;
    }

    public void setInterest_details(ArrayList<Interest_details> interest_details) {
        this.interest_details = interest_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
