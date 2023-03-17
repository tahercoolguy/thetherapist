package com.master.design.therapist.DataModel;

import java.util.ArrayList;
import java.util.List;

public class TherapistAgeDM {

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
