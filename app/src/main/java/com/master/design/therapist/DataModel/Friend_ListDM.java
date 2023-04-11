package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class Friend_ListDM {
    private String msg;
    private ArrayList<All_friends> all_friends;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<All_friends> getAll_friends() {
        return all_friends;
    }

    public void setAll_friends(ArrayList<All_friends> all_friends) {
        this.all_friends = all_friends;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
