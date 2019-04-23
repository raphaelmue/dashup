package de.dashup.shared.models;

import com.google.gson.annotations.SerializedName;

public class DatabaseSection extends DatabaseObject implements Order{

    private Integer id;
    @SerializedName("user_id")
    private Integer userID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String name;

    public DatabaseSection(Integer id, Integer userID, Integer predecessorID, String name) {
        this.id = id;
        this.userID = userID;
        this.predecessorID = predecessorID;
        this.name = name;
    }

    public Integer getID() {
        return this.id;
    }

    public Integer getUserID() {
        return this.userID;
    }

    public Integer getPredecessorID() {
        return this.predecessorID;
    }

    public String getName() {
        return this.name;
    }

}