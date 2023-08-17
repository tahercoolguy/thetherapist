package com.master.design.therapist.DataModel;

public class Forgotpassword {

    private String status;
    private String msg;
    private String reset_link;

    public Forgotpassword(String status, String msg, String reset_link) {
        this.status = status;
        this.msg = msg;
        this.reset_link = reset_link;
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

    public String getReset_link() {
        return reset_link;
    }

    public void setReset_link(String reset_link) {
        this.reset_link = reset_link;
    }
}
