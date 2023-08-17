package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class ChatHistoryDM {

    private String msg;
    private ArrayList<All_messages> all_messages;
    private String status;
    private Boolean friends;

    public Boolean getFriends() {
        return friends;
    }

    public void setFriends(Boolean friends) {
        this.friends = friends;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<All_messages> getAll_messages() {
        return all_messages;
    }

    public void setAll_messages(ArrayList<All_messages> all_messages) {
        this.all_messages = all_messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
