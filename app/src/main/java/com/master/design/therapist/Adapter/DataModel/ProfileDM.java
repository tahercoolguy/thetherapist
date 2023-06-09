package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class ProfileDM
{
    private String msg;
    private ArrayList<User_data> user_data;
    private String status;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<User_data> getUser_data() {
        return user_data;
    }

    public void setUser_data(ArrayList<User_data> user_data) {
        this.user_data = user_data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
