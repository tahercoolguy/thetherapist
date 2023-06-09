package com.master.design.therapist.Adapter.DataModel;

public class RemoveImageRoot {

     
        public String status ;
        public String msg ;

    public RemoveImageRoot(String status, String msg) {
        this.status = status;
        this.msg = msg;
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
}
