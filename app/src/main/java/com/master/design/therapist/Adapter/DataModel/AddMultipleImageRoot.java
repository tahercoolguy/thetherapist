package com.master.design.therapist.Adapter.DataModel;

public class AddMultipleImageRoot {

        public String status ;
        public String message ;

    public AddMultipleImageRoot(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
