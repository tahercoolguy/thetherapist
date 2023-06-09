package com.master.design.therapist.Adapter.DataModel;

import java.util.ArrayList;

public class ChatHistoryRoot {

    public String status ;
    public String msg ;
    public ArrayList<All_messages> all_messages ;

    public ChatHistoryRoot(String status, String msg, ArrayList<All_messages> all_messages) {
        this.status = status;
        this.msg = msg;
        this.all_messages = all_messages;
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

    public ArrayList<All_messages> getAll_messages() {
        return all_messages;
    }

    public void setAll_messages(ArrayList<All_messages> all_messages) {
        this.all_messages = all_messages;
    }
}
