package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSectionWidgets extends DatabaseObject implements Order{

    private int id;
    @SerializedName("section_id")
    private int sectionID;
    @SerializedName("panel_id")
    private int panelID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String size;

    public DatabaseSectionWidgets(int id, int sectionID, int panelID, Integer predecessorID, String size) {
        this.id = id;
        this.sectionID = sectionID;
        this.panelID = panelID;
        this.predecessorID = predecessorID;
        this.size = size;
    }

    public int getID() {
        return id;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getPanelID() {
        return panelID;
    }

    public Integer getPredecessorID() {
        return predecessorID;
    }

    public String getSize() {
        return size;
    }

}