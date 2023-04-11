package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class Request_ListDM {
    private String msg;
    private ArrayList<Senders> senders;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Senders> getSenders() {
        return senders;
    }

    public void setSenders(ArrayList<Senders> senders) {
        this.senders = senders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
