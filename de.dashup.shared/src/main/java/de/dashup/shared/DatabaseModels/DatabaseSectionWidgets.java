package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;

public class DatabaseSectionWidgets extends DatabaseObject implements Order{

    private Integer id;
    @SerializedName("section_id")
    private Integer sectionID;
    @SerializedName("panel_id")
    private Integer panelID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String size;

    public DatabaseSectionWidgets(Integer id, Integer sectionID, Integer panelID, Integer predecessorID, String size) {
        this.id = id;
        this.sectionID = sectionID;
        this.panelID = panelID;
        this.predecessorID = predecessorID;
        this.size = size;
    }

    public Integer getID() {
        return id;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public Integer getPanelID() {
        return panelID;
    }

    public Integer getPredecessorID() {
        return predecessorID;
    }

    public String getSize() {
        return size;
    }

}