package com.master.design.therapist.DataModel;

public class ActiveStatusRoot {

    private String status ;
    private ActiveStatus active_status ;

    public ActiveStatusRoot(String status, ActiveStatus active_status) {
        this.status = status;
        this.active_status = active_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ActiveStatus getActive_status() {
        return active_status;
    }

    public void setActive_status(ActiveStatus active_status) {
        this.active_status = active_status;
    }
}
