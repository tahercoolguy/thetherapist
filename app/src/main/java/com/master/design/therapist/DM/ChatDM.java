package com.master.design.therapist.DM;

public class ChatDM {
    private  String name;
    private  String time;
    private  String messeageCount;
    private  String messeage;
    private  int image;

    public ChatDM(String name, String time, String messeageCount, String messeage, int image) {
        this.name = name;
        this.time = time;
        this.messeageCount = messeageCount;
        this.messeage = messeage;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMesseageCount() {
        return messeageCount;
    }

    public void setMesseageCount(String messeageCount) {
        this.messeageCount = messeageCount;
    }

    public String getMesseage() {
        return messeage;
    }

    public void setMesseage(String messeage) {
        this.messeage = messeage;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
