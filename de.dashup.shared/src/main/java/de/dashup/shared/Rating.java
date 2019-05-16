package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class Rating implements DatabaseObject {
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("panel_id")
    private int panelId;
    private int rating;
    private String title;
    private String text;
    @SerializedName("changed_on")
    private String changeDate;
    @SerializedName("name")
    private String userName;
    @SerializedName("surname")
    private String userSurname;


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int getId() {
        return id;
    }

}
