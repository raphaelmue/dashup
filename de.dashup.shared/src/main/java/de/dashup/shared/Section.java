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

    public Section(int user_id, int section_id, String section_name, int predecessorID) {
        this.sectionID = section_id;
        this.section_name = section_name;
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
