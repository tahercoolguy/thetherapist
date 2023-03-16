package com.master.design.therapist.DataModel;

import android.telecom.Call;

import java.util.ArrayList;

public class TherapistRegisterDM {

    private ArrayList<Details> details;
    private String status;

    public ArrayList<Details> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Details> details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
