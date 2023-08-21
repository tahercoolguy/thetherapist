package com.master.design.therapist.DataModel;

public class AgeMyProfile {

    private String id;
    private String age_eg;
    private String age_ar;

    public AgeMyProfile(String id, String age_eg, String age_ar) {
        this.id = id;
        this.age_eg = age_eg;
        this.age_ar = age_ar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge_eg() {
        return age_eg;
    }

    public void setAge_eg(String age_eg) {
        this.age_eg = age_eg;
    }

    public String getAge_ar() {
        return age_ar;
    }

    public void setAge_ar(String age_ar) {
        this.age_ar = age_ar;
    }
}
