package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSection extends DatabaseObject implements Order{

    @SerializedName("section_id")
    private int sectionID;
    @SerializedName("user_id")
    private int userID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String name;

    public DatabaseSection(int sectionID, int userID, Integer predecessorID, String name) {
        this.sectionID = sectionID;
        this.userID = userID;
        this.predecessorID = predecessorID;
        this.name = name;
    }

    public int getID() {
        return this.sectionID;
    }

    public int getUserID() {
        return this.userID;
    }

    public Integer getPredecessorID() {
        return this.predecessorID;
    }

    public String getName() {
        return this.name;
    }

}