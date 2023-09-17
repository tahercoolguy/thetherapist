package com.master.design.therapist.DataModel;

public class ReportUserRoot {

    private String status ;
    private String msg ;
    private ReportUserDetails details ;

    public ReportUserRoot(String status, String msg, ReportUserDetails details) {
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

    public ReportUserDetails getDetails() {
        return details;
    }

    public void setDetails(ReportUserDetails details) {
        this.details = details;
    }
}
