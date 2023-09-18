package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class ChatHistoryDM {

    private String msg;
    private ArrayList<All_messages> all_messages;
    private String status;
    private Boolean friends;
    private Boolean block_status;

    public ChatHistoryDM(String msg, ArrayList<All_messages> all_messages, String status, Boolean friends, Boolean block_status) {
        this.msg = msg;
        this.all_messages = all_messages;
        this.status = status;
        this.friends = friends;
        this.block_status = block_status;
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

    public Boolean getFriends() {
        return friends;
    }

    public void setFriends(Boolean friends) {
        this.friends = friends;
    }

    public Boolean getBlock_status() {
        return block_status;
    }

    public void setBlock_status(Boolean block_status) {
        this.block_status = block_status;
    }
}
