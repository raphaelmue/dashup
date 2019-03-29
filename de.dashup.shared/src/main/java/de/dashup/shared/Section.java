package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section implements DatabaseObject {
    @SerializedName("section_id")
    private int sectionID;
    private String section_name;
    @SerializedName("predecessor_id")
    private int predecessorID;
    private List<Panel> panels;

    public Section() {
    }

    public Section(int sectionId, String sectionName, int predecessorID) {
        this.sectionID = sectionId;
        this.section_name = sectionName;
        this.predecessorID = predecessorID;
    }

    public void setId(int id) {
        this.sectionID = id;
    }

    public void setName(String name) {
        this.section_name = name;
    }

    public void setPanels(List<Panel> panels) {
        this.panels = panels;
    }

    public String getName() {
        return section_name;
    }

    public List<Panel> getPanels() {
        return panels;
    }

    public int getPredecessor() {
        return predecessorID;
    }

    @Override
    public int getId() {
        return sectionID;
    }
}
