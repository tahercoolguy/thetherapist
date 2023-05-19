package com.master.design.therapist.DataModel;

public class All_messages {

    private String receiver_user_id;
    private String id;
    private String message;
    private String sender_user_id;
    private String timestamp;
    private String status;

//    public All_messages(String receiver_user_id, String id, String message, String sender_user_id, String timestamp, String status) {
//        this.receiver_user_id = receiver_user_id;
//        this.id = id;
//        this.message = message;
//        this.sender_user_id = sender_user_id;
//        this.timestamp = timestamp;
//        this.status = status;
//    }

    public String getReceiver_user_id() {
        return receiver_user_id;
    }

    public void setReceiver_user_id(String receiver_user_id) {
        this.receiver_user_id = receiver_user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender_user_id() {
        return sender_user_id;
    }

    public void setSender_user_id(String sender_user_id) {
        this.sender_user_id = sender_user_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
