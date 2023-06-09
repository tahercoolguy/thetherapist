package com.master.design.therapist.Adapter.DataModel.MyProfile;

public class NewEtnicity {

    public String id ;
    public String name_en ;
    public String name_ar ;

    public NewEtnicity(String id, String name_en, String name_ar) {
        this.id = id;
        this.name_en = name_en;
        this.name_ar = name_ar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
