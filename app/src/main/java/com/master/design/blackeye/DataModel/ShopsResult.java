package com.master.design.blackeye.DataModel;

public class ShopsResult {
    private String c_date;

    private String[] attachment;

    private String description;

    private String image_file;

    private String id;

    private String tag;

    private String shop_name;

    private String mobile_number;

    private String status;

    public String getC_date ()
    {
        return c_date;
    }

    public void setC_date (String c_date)
    {
        this.c_date = c_date;
    }

    public String[] getAttachment ()
    {
        return attachment;
    }

    public void setAttachment (String[] attachment)
    {
        this.attachment = attachment;
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

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    public String getShop_name ()
    {
        return shop_name;
    }

    public void setShop_name (String shop_name)
    {
        this.shop_name = shop_name;
    }

    public String getMobile_number ()
    {
        return mobile_number;
    }

    public void setMobile_number (String mobile_number)
    {
        this.mobile_number = mobile_number;
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
