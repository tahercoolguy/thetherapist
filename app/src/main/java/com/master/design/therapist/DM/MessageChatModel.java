package com.master.design.therapist.DM;

public class MessageChatModel {
    private String text;
    private String time;
    private int image;
    private int viewType;

    public MessageChatModel(String text, String time, int viewType,int image) {
        this.text = text;
        this.time = time;
        this.image = image;
        this.viewType = viewType;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public int getViewType() {
        return viewType;
    }
}