package com.master.design.blackeye.DataModel;

public class BannerDM {
    private BannerResult result;

    private String message;

    private String status;

    public BannerResult getResult ()
    {
        return result;
    }

    public void setResult (BannerResult result)
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
