package com.master.design.blackeye.DataModel;

import java.util.ArrayList;

public class EventsDM {
    private ArrayList<EventsResult> result;

    private String message;

    private String status;

    public ArrayList<EventsResult> getResult ()
    {
        return result;
    }

    public void setResult (ArrayList<EventsResult> result)
    {
        this.result = result;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
