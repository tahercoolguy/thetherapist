package com.master.design.therapist.Adapter.DataModel.MyProfile;

import java.util.ArrayList;

public class Root {
    public String status ;
    public String msg ;
    public ArrayList<UserDatum> user_data ;

    public Root(String status, String msg, ArrayList<UserDatum> user_data) {
        this.status = status;
        this.msg = msg;
        this.user_data = user_data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<UserDatum> getUser_data() {
        return user_data;
    }

    public void setUser_data(ArrayList<UserDatum> user_data) {
        this.user_data = user_data;
    }
}
