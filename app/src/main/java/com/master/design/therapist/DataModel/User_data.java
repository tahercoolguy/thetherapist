package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class User_data {

    public String image;
    //    private String gender;
    public String phone;
    public String dob;
    public String name;
    public String id;
    public String email;
    public ArrayList<Gender> gender;
    public ArrayList<Country> country;

    public User_data(String image, String phone, String dob, String name, String id, String email, ArrayList<Gender> gender, ArrayList<Country> country) {
        this.image = image;
        this.phone = phone;
        this.dob = dob;
        this.name = name;
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Gender> getGender() {
        return gender;
    }

    public void setGender(ArrayList<Gender> gender) {
        this.gender = gender;
    }

    public ArrayList<Country> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<Country> country) {
        this.country = country;
    }
}
