package com.master.design.therapist.Adapter.DataModel.MyProfile;

import java.util.ArrayList;

public class UserDatum {
    public int id;
    public String image;
    public String name;
    public String email;
    public String phone;
    public NewGender gender;
    public String dob;
    public NewCountry country;
    public NewEducation education;
    public NewEtnicity ethnicity;
    public ArrayList<NewInterest> interests;
    public String aboutyou;

    public UserDatum(int id, String image, String name, String email, String phone, NewGender gender, String dob, NewCountry country, NewEducation education, NewEtnicity ethnicity, ArrayList<NewInterest> interests, String aboutyou) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.country = country;
        this.education = education;
        this.ethnicity = ethnicity;
        this.interests = interests;
        this.aboutyou = aboutyou;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public NewGender getGender() {
        return gender;
    }

    public void setGender(NewGender gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public NewCountry getCountry() {
        return country;
    }

    public void setCountry(NewCountry country) {
        this.country = country;
    }

    public NewEducation getEducation() {
        return education;
    }

    public void setEducation(NewEducation education) {
        this.education = education;
    }

    public NewEtnicity getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(NewEtnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public ArrayList<NewInterest> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<NewInterest> interests) {
        this.interests = interests;
    }

    public String getAboutyou() {
        return aboutyou;
    }

    public void setAboutyou(String aboutyou) {
        this.aboutyou = aboutyou;
    }
}
