package de.dashup.shared.layout;

import com.google.gson.annotations.SerializedName;
import de.dashup.shared.DatabaseObject;

import java.util.List;

public class Section implements DatabaseObject,Comparable<Section> {
    @SerializedName("id")
    private int id;
    private String section_name;
    @SerializedName("section_index")
    private int index;
    private List<Widget> widgets;

    public Section() {
    }

    public Section(int sectionId, String sectionName, int index) {
        this.id = sectionId;
        this.section_name = sectionName;
        this.index = index;
    }

    public void setId(int id) {
        this.id = id;
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
        return id;
    }

    @Override
    public int compareTo(Section sectionToCompare) {
        return Integer.compare(this.getIndex(), sectionToCompare.getIndex());
    }
}
