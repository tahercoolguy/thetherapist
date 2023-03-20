package com.master.design.therapist.Adapter;

import java.util.ArrayList;

public class TherapistEducationDM {
    private ArrayList<Education_details> education_details;
    private String status;

    public ArrayList<Education_details> getEducation_details() {
        return education_details;
    }

    public void setEducation_details(ArrayList<Education_details> education_details) {
        this.education_details = education_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
