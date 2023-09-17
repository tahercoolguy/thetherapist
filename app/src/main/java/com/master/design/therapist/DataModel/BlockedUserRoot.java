package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class BlockedUserRoot {

    private String status ;
    private String msg ;
    private ArrayList<BlockedUserDetail> details ;

    public BlockedUserRoot(String status, String msg, ArrayList<BlockedUserDetail> details) {
        this.status = status;
        this.msg = msg;
        this.details = details;
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

    public ArrayList<BlockedUserDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<BlockedUserDetail> details) {
        this.details = details;
    }
}
