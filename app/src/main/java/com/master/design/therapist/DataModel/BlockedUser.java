package com.master.design.therapist.DataModel;

public class BlockedUser {

    private String id ;
    private String name ;
    private String dob ;
    private String country ;
    private String phone ;
    private String email ;
    private String password ;
     public String confirm_password ;
    public String Stringerests ;
    public String image ;
    public String aboutyou ;
    public String online_status ;
    public String active_status ;
    public String device_type ;
    public String device_token ;
    public String repeated_at ;
    public String updated_at ;
    public String gender ;
    public String ethnicity ;
    public String age ;
    public String education ;

    public BlockedUser(String id, String name, String dob, String country, String phone, String email, String password, String confirm_password, String stringerests, String image, String aboutyou, String online_status, String active_status, String device_type, String device_token, String repeated_at, String updated_at, String gender, String ethnicity, String age, String education) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        Stringerests = stringerests;
        this.image = image;
        this.aboutyou = aboutyou;
        this.online_status = online_status;
        this.active_status = active_status;
        this.device_type = device_type;
        this.device_token = device_token;
        this.repeated_at = repeated_at;
        this.updated_at = updated_at;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.age = age;
        this.education = education;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getStringerests() {
        return Stringerests;
    }

    public void setStringerests(String stringerests) {
        Stringerests = stringerests;
    }

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

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getRepeated_at() {
        return repeated_at;
    }

    public void setRepeated_at(String repeated_at) {
        this.repeated_at = repeated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
