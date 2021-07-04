package com.master.design.blackeye.DataModel;

public class BannerResult {
    private String c_date;

    private String id;

    private String title;

    private String banner_file;

    private String status;

    public String getC_date ()
    {
        return c_date;
    }

    public void setC_date (String c_date)
    {
        this.c_date = c_date;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getBanner_file ()
    {
        return banner_file;
    }

    public void setBanner_file (String banner_file)
    {
        this.banner_file = banner_file;
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
