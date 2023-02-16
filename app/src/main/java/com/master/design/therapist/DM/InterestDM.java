package com.master.design.therapist.DM;

public class InterestDM {
    private  String tittleInterest;
    private  int image;

    public InterestDM(String tittleInterest, int image) {
        this.tittleInterest = tittleInterest;
        this.image = image;
    }

    public String getTittleInterest() {
        return tittleInterest;
    }

    public void setTittleInterest(String tittleInterest) {
        this.tittleInterest = tittleInterest;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
