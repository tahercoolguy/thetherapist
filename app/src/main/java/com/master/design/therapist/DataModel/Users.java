package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class Users {

    private String image;
    private String aboutyou;
    private String name;
    private String id;
    private ArrayList<Interests> interests;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAboutyou() {
        return aboutyou;
    }

    public void setAboutyou(String aboutyou) {
        this.aboutyou = aboutyou;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Interests> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Interests> interests) {
        this.interests = interests;
    }
}
