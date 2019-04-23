package de.dashup.shared.models;

import com.google.gson.annotations.SerializedName;

public class DatabaseToken extends DatabaseObject{

    private Integer id;
    @SerializedName("user_id")
    private Integer userID;
    private String token;
    @SerializedName("expiry_date")
    private String expiryDate;

    public DatabaseToken(Integer id, Integer userID, String token, String expiryDate) {
        this.id = id;
        this.userID = userID;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public Integer getID() {
        return this.id;
    }

    public Integer getUserID() {
        return this.userID;
    }

    public String getToken() {
        return this.token;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

}