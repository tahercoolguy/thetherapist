package com.master.design.therapist.DataModel;

public class BlockedUserDetail {

    private String id ;
    private String is_blocked ;
    private String user ;
    private BlockedUser blocked_user ;

    public BlockedUserDetail(String id, String is_blocked, String user, BlockedUser blocked_user) {
        this.id = id;
        this.is_blocked = is_blocked;
        this.user = user;
        this.blocked_user = blocked_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BlockedUser getBlocked_user() {
        return blocked_user;
    }

    public void setBlocked_user(BlockedUser blocked_user) {
        this.blocked_user = blocked_user;
    }
}
