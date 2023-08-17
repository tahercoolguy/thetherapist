package com.master.design.therapist.DataModel;

import java.util.ArrayList;

public class NewsDM {
    private ArrayList<NewsResult> result;

    private String message;

    private String status;

    public ArrayList<NewsResult> getResult ()
    {
        return result;
    }

    public void setResult (ArrayList<NewsResult> result)
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
