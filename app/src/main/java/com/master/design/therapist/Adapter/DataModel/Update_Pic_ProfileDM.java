package com.master.design.therapist.Adapter.DataModel;

import java.util.List;

public class Update_Pic_ProfileDM {

    private String msg;
    private List<Details> details;
    private String status;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
