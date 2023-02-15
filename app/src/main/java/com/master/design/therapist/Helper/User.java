package com.master.design.therapist.Helper;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.master.design.therapist.Utils.MyCommon;


public class User {

    public static final String TYPE_NORMAL = "user";


    private SharedPreferences sp;
    private Context context;

    private static Language language;

    public User(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("com.agent.player", Context.MODE_PRIVATE);
    }

    public String getSessionID(){ return sp.getString("sessionid", String.valueOf(MyCommon.number));}

    public void setSessionID(String sessionid){
        sp.edit().putString("sessionid", sessionid).apply();
    }


    public int getId() {
        return sp.getInt("user_id", 0);
    }

    public void setUserType(String user_type) {
        sp.edit().putString("user_type", user_type).apply();
    }

    public String getUserType() {
        return sp.getString("user_type", User.TYPE_NORMAL);
    }

    public void setId(int user_id) {
        sp.edit().putInt("user_id", user_id).apply();
    }

    public String getName() {
        return sp.getString("name", null);
    }

    public void setName(String name) {
        sp.edit().putString("name", name).apply();
    }

    public String getPassword() {
        return sp.getString("password", null);
    }

    public void setPassword(String password) {
        sp.edit().putString("password", password).apply();
    }

    public String getImage() {
        return sp.getString("image", null);
    }

    public void setImage(String image) {
        sp.edit().putString("image", image).apply();
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
        sp.edit().remove("user_id").apply();
        sp.edit().remove("name").apply();
        sp.edit().remove("mobile_no").apply();
        sp.edit().remove("user_type").apply();
        sp.edit().remove("notification").apply();
    }

    public boolean isLanguageShow() {
        return sp.getBoolean("language_show", true);
    }

    public void setLanguageShow(boolean isShow) {
        sp.edit().putBoolean("language_show", isShow).apply();
    }
}

