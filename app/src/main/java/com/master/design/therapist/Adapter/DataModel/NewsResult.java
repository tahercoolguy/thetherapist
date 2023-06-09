package com.master.design.therapist.Adapter.DataModel;

public class NewsResult {
    private String c_date;

    private String heading;

    private String description;

    private String image_file;

    private String id;

    private String type;

    private String status;

    public String getC_date ()
    {
        return c_date;
    }

    public void setC_date (String c_date)
    {
        this.c_date = c_date;
    }

    public String getHeading ()
    {
        return heading;
    }

    public void setHeading (String heading)
    {
        this.heading = heading;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage_file ()
    {
        return image_file;
    }

    public void setImage_file (String image_file)
    {
        this.image_file = image_file;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
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
