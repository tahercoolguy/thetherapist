package com.master.design.therapist.DataModel;

public class ActiveStatus {

    private String id;
    private String active_status;

    public ActiveStatus(String id, String active_status) {
        this.id = id;
        this.active_status = active_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }
}
