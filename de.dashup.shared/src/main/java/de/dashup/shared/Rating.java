package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class Rating implements DatabaseObject {
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("panel_id")
    private int panelId;
    @SerializedName("rating")
    private int ratingValue;
    private String title;
    private String text;
    @SerializedName("changed_on")
    private String changeDate;
    @SerializedName("user_name")
    private String userName;

    public Rating() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
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

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }
}
