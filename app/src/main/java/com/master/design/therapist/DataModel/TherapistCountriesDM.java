package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class TherapistCountriesDM {

    private ArrayList<Details> details;
    private String status;
    private String msg;


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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
