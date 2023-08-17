package com.master.design.therapist.DataModel.MyProfile;

public class NewCountry {
    public String isoCode ;
    public String name_en ;
    public String name_ar ;

    public NewCountry(String isoCode, String name_en, String name_ar) {
        this.isoCode = isoCode;
        this.name_en = name_en;
        this.name_ar = name_ar;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }
}
