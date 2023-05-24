package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class TherapistLoginDM {

    private String msg;
//    private ArrayList<LoginUser> user;
    private String status;
    public UserDM user ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDM getUser() {
        return user;
    }

    public void setUser(UserDM user) {
        this.user = user;
    }
}
