package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSetting extends DatabaseObject{

    private int id;
    @SerializedName("user_id")
    private int userID;
    @SerializedName("background_image")
    private String backgroundImage;
    private String theme;
    private String language;

    public DatabaseSetting(int id, int userID, String backgroundImage, String theme, String language) {
        this.id = id;
        this.userID = userID;
        this.backgroundImage = backgroundImage;
        this.theme = theme;
        this.language = language;
    }

    public int getID() {
        return id;
    }

    public int getUserID() {
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