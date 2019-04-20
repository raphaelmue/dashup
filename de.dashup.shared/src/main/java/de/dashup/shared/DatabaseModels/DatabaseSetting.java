package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSetting extends DatabaseObject{

    private Integer id;
    @SerializedName("user_id")
    private Integer userID;
    @SerializedName("background_image")
    private String backgroundImage;
    private String theme;
    private String language;

    public DatabaseSetting(Integer id, Integer userID, String backgroundImage, String theme, String language) {
        this.id = id;
        this.userID = userID;
        this.backgroundImage = backgroundImage;
        this.theme = theme;
        this.language = language;
    }

    public Integer getID() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getTheme() {
        return theme;
    }

    public String getLanguage() {
        return language;
    }

}