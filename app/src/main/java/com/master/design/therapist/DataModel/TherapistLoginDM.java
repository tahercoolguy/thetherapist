package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class TherapistLoginDM {

    private String msg;
    private ArrayList<LoginUser> user;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<LoginUser> getUser() {
        return user;
    }

    public void setUser(ArrayList<LoginUser> user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
