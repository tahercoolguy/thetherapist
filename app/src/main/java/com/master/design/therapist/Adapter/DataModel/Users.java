package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class Users {



    private String aboutyou;
    private String name;
    private String id;
    private ArrayList<Interests> interests;
    private ArrayList<Image> image;

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    public ArrayList<Image> getImage() {
        return image;
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
