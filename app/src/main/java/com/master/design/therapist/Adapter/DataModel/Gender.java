package com.master.design.therapist.Adapter.DataModel;

public class Gender {
    public String id ;
    public String gender_eg ;
    public String gender_arb ;

    public Gender(String id, String gender_eg, String gender_arb) {
        this.id = id;
        this.gender_eg = gender_eg;
        this.gender_arb = gender_arb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender_eg() {
        return gender_eg;
    }

    public void setGender_eg(String gender_eg) {
        this.gender_eg = gender_eg;
    }

    public String getGender_arb() {
        return gender_arb;
    }

    public void setGender_arb(String gender_arb) {
        this.gender_arb = gender_arb;
    }
}
