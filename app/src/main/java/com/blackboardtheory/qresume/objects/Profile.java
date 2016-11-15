package com.blackboardtheory.qresume.objects;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class Profile {



    private String title, icon_url;
    private int userID;


    public Profile(String title, String icon_url, int userID) {
        this.title = title;
        this.icon_url = icon_url;
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconURL() {
        return icon_url;
    }

    public void setIconURL(String icon_url) {
        this.icon_url = icon_url;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
