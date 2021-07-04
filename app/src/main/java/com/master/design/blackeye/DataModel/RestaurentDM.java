package com.master.design.blackeye.DataModel;

import java.util.ArrayList;

public class RestaurentDM {
    private ArrayList<RestaurentResult> result;

    private String message;

    private String status;

    public ArrayList<RestaurentResult> getResult ()
    {
        return result;
    }

    public void setResult (ArrayList<RestaurentResult> result)
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
