package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSection extends DatabaseObject implements Order{

    @SerializedName("section_id")
    private Integer sectionID;
    @SerializedName("user_id")
    private Integer userID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String name;

    public DatabaseSection(Integer sectionID, Integer userID, Integer predecessorID, String name) {
        this.sectionID = sectionID;
        this.userID = userID;
        this.predecessorID = predecessorID;
        this.name = name;
    }

    public Integer getID() {
        return this.sectionID;
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