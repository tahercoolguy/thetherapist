package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class CommonReasonRoot {
    private String status ;
    private String msg ;
    private ArrayList<CommonReasonDetail> details;

    public CommonReasonRoot(String status, String msg, ArrayList<CommonReasonDetail> details) {
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

    public ArrayList<CommonReasonDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<CommonReasonDetail> details) {
        this.details = details;
    }
}
