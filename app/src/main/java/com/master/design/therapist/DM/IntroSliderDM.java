package com.master.design.therapist.DM;

public class IntroSliderDM {
    private  String tittleTxt;
    private  String descriptionTxt;
    private  int image;

    public IntroSliderDM(String tittleTxt, String descriptionTxt, int image) {
        this.tittleTxt = tittleTxt;
        this.descriptionTxt = descriptionTxt;
        this.image = image;
    }

    public String getTittleTxt() {
        return tittleTxt;
    }

    public void setTittleTxt(String tittleTxt) {
        this.tittleTxt = tittleTxt;
    }

    public String getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(String descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
