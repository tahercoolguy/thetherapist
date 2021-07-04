package com.master.design.blackeye.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.master.design.blackeye.Utils.MyCommon;

public class User  {

    public static final String TYPE_PATIENT = "Patient";
    public static final String TYPE_SURGEON = "Surgeon";
    public static final String Hospital_Val="Hospital";
    public static final String Clinic_Val="Clinic";
    private SharedPreferences sp;
    private Context context;

    private static Language language;

    public User(Context context) {
        this.context = context;
        try {
            sp = context.getSharedPreferences("com.master.design.eightyeighty", Context.MODE_PRIVATE);
        }catch (Exception e)
        {

        }
    }

    public String getSessionID(){ return sp.getString("sessionid", String.valueOf(MyCommon.number));}

    public void setSessionID(String sessionid){
        sp.edit().putString("sessionid", sessionid).apply();
    }


    public int getId() {
        return sp.getInt("userid", 0);
    }

    public void setUserType(String usertype) {
        sp.edit().putString("usertype", usertype).apply();
    }

    public String getUserType() {
        return sp.getString("usertype", User.TYPE_PATIENT);
    }

    public void setId(int userid) {
        sp.edit().putInt("userid", userid).apply();
    }

    public String getCountry() {
        return sp.getString("country", null);
    }

    public void setCountry(String name) {
        sp.edit().putString("country", name).apply();
    }


    public String getCountryId() {
        return sp.getString("countryid", null);
    }

    public void setCountryId(String name) {
        sp.edit().putString("countryid", name).apply();
    }




    public String getUserToken() {
        return sp.getString("usertoken", null);
    }

    public void setusertoken(String usertoken) {
        sp.edit().putString("usertoken", usertoken).apply();
    }
    public String getLastName() {
        return sp.getString("lastname", null);
    }

    public void setLastName(String name) {
        sp.edit().putString("lastname", name).apply();
    }

    public String getPassword() {
        return sp.getString("password", null);
    }

    public void setPassword(String password) {
        sp.edit().putString("password", password).apply();
    }

    public String getImage() {
        return sp.getString("avadarimgurl", null);
    }

    public void setImage(String image) {
        sp.edit().putString("avadarimgurl", image).apply();
    }

    public String getEmail() {
        return sp.getString("email", null);
    }

    public void setEmail(String email) {
        sp.edit().putString("email", email).apply();
    }

    public boolean isNotification() {
        return sp.getBoolean("notification", true);
    }

    public void setNotification(boolean bool) {
        sp.edit().putBoolean("notification", bool).apply();
    }

    public String getMobileNumber() {
        return sp.getString("mobile_no", null);
    }

    public void setMobileNumber(String mobile_no) {
        sp.edit().putString("mobile_no", mobile_no).apply();
    }

    //language
    public Language setLanguage(Language language) {
        User.language = language;
        sp.edit().putString("language", new Gson().toJson(language)).apply();
        return language;
    }

    private Language getLanguage() {
        if (User.language == null) {
            String language = sp.getString("language", null);
            if (language == null) {
                return setLanguage(Language.GET_ENGLISH());
            } else {
                User.language = new Gson().fromJson(language, Language.class);
                return User.language;
            }
        } else {
            return User.language;
        }
    }

    public int getLanguage_Id() {
        language = getLanguage();
        if (language == null) {
            return Language.GET_ENGLISH().getId();
        } else {
            return language.getId();
        }
    }

    public String getLanguageCode() {
        Language language = getLanguage();
        return language.getCode();
    }

    public void logout() {
        sp.edit().remove("userid").apply();
        sp.edit().remove("firstname").apply();
        sp.edit().remove("lastname").apply();
        sp.edit().remove("sessionid").apply();
        sp.edit().remove("usertype").apply();
        sp.edit().remove("usertoken").apply();

        sp.edit().remove("notification").apply();
    }

    public boolean isLanguageShow() {
        return sp.getBoolean("language_show", true);
    }

    public void setLanguageShow(boolean isShow) {
        sp.edit().putBoolean("language_show", isShow).apply();
    }
}