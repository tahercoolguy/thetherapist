package com.master.design.therapist.DataModel.MyProfile;

public class NewInterest {
    public String id ;
    public String interest_img ;
    public String interest_eg ;
    public String interest_arb ;

    public NewInterest(String id, String interest_img, String interest_eg, String interest_arb) {
        this.id = id;
        this.interest_img = interest_img;
        this.interest_eg = interest_eg;
        this.interest_arb = interest_arb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterest_img() {
        return interest_img;
    }

    public void setInterest_img(String interest_img) {
        this.interest_img = interest_img;
    }

    public String getInterest_eg() {
        return interest_eg;
    }

    public void setInterest_eg(String interest_eg) {
        this.interest_eg = interest_eg;
    }

    public String getInterest_arb() {
        return interest_arb;
    }

    public void setInterest_arb(String interest_arb) {
        this.interest_arb = interest_arb;
    }
}
