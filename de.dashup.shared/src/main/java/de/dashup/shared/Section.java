package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section implements DatabaseObject,Comparable<Section> {
    @SerializedName("section_id")
    private int sectionID;
    private String section_name;
    @SerializedName("section_index")
    private int index;
    private List<Widget> widgets;

    public Section() {
    }

    public Section(int sectionId, String sectionName, int index) {
        this.sectionID = sectionId;
        this.section_name = sectionName;
        this.index = index;
    }

    public void setId(int id) {
        this.sectionID = id;
    }

    public void setName(String name) {
        this.section_name = name;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public String getName() {
        return section_name;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getId() {
        return sectionID;
    }

    @Override
    public int compareTo(Section sectionToCompare) {
        if(index > sectionToCompare.getIndex()){
            return 1;
        }
        return -1;
    }
}
