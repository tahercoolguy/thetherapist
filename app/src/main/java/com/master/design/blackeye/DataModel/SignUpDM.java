package com.master.design.blackeye.DataModel;

public class SignUpDM {
    private SignUpResult result;

    private String message;

    private String status;

    public SignUpResult getResult ()
    {
        return result;
    }

    public void setResult (SignUpResult result)
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
