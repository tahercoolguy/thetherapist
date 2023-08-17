package com.master.design.therapist.DataModel.GetAll_Image;

public class GetAllImageOutput {
    public String id ;
    public String other_image ;
    public String the_user ;

    public GetAllImageOutput(String id, String other_image, String the_user) {
        this.id = id;
        this.other_image = other_image;
        this.the_user = the_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOther_image() {
        return other_image;
    }

    public void setOther_image(String other_image) {
        this.other_image = other_image;
    }

    public String getThe_user() {
        return the_user;
    }

    public void setThe_user(String the_user) {
        this.the_user = the_user;
    }
}
