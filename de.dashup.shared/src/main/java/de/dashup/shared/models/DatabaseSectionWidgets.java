package de.dashup.shared.models;

import com.google.gson.annotations.SerializedName;

public class DatabaseSectionWidgets extends DatabaseObject implements Order{

    private Integer id;
    @SerializedName("section_id")
    private Integer sectionID;
    @SerializedName("widget_id")
    private Integer widgetID;
    @SerializedName("predecessor_id")
    private Integer predecessorID;
    private String size;

    public DatabaseSectionWidgets(Integer id, Integer sectionID, Integer widgetID, Integer predecessorID, String size) {
        this.id = id;
        this.sectionID = sectionID;
        this.widgetID = widgetID;
        this.predecessorID = predecessorID;
        this.size = size;
    }

    public Integer getID() {
        return this.id;
    }

    public Integer getSectionID() {
        return this.sectionID;
    }

    public Integer getWidgetID() {
        return this.widgetID;
    }

    public Integer getPredecessorID() {
        return this.predecessorID;
    }

    public String getSize() {
        return this.size;
    }

}