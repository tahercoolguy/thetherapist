package com.master.design.blackeye.DataModel;

import java.util.ArrayList;

public class ShopsDM {
    private ArrayList<ShopsResult> result;

    private String message;

    private String status;

    public ArrayList<ShopsResult> getResult ()
    {
        return result;
    }

    public void setResult (ArrayList<ShopsResult> result)
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
