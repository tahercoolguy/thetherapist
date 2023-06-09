package com.master.design.therapist.Adapter.DataModel.GetAll_Image;

import java.util.ArrayList;

public class GetAllImageRoot {

    public String status;
    public String msg;
    public ArrayList<GetAllImageOutput> all_images;

    public GetAllImageRoot(String status, String msg, ArrayList<GetAllImageOutput> all_images) {
        this.status = status;
        this.msg = msg;
        this.all_images = all_images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<GetAllImageOutput> getAll_images() {
        return all_images;
    }

    public void setAll_images(ArrayList<GetAllImageOutput> all_images) {
        this.all_images = all_images;
    }
}
