package com.master.design.therapist.Helper;

import com.google.gson.annotations.SerializedName;


public class Language {
    @SerializedName("language_id")
    private int id;
    @SerializedName("language_name")
    private String name;
    @SerializedName("language_image")
    private String image;
    @SerializedName("language_code")
    private String code;
    @SerializedName("is_default")
    private int defualt;


    public Language(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Language(int id, String image, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDefualt() {
        return defualt;
    }

    public void setDefualt(int defualt) {
        this.defualt = defualt;
    }

    public static Language GET_ENGLISH() {
        return new Language(1, "English", "en");
    }
}
