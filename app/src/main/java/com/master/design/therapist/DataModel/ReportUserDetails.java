package com.master.design.therapist.DataModel;

public class ReportUserDetails {

    private String id ;
    private String description ;
    private String created_at ;
    private String user ;
    private String reported_user ;
    private String reason ;

    public ReportUserDetails(String id, String description, String created_at, String user, String reported_user, String reason) {
        this.id = id;
        this.description = description;
        this.created_at = created_at;
        this.user = user;
        this.reported_user = reported_user;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReported_user() {
        return reported_user;
    }

    public void setReported_user(String reported_user) {
        this.reported_user = reported_user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
