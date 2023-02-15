package com.master.design.therapist.Models;

public class DrawerMenu {
    public static final int RECORD = 0, NEWS = 1, BUSINESS = 2, EVENTS = 3, JOBS = 4, PROFESSIONALS = 5, CHAT = 6, Contact_Us = 7, Rate_this_app = 8, Feedback = 9, SETTINGS = 10,Package_Subscription=11,EducationMaterial=12,SignOut=13,Gallery=14,Conference=15;
    private int id;
    private int icon;
    private String name;
    private int badge;
    private boolean Selected=false;
    public DrawerMenu(int id, int icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public DrawerMenu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public boolean isSelected() {

        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}

