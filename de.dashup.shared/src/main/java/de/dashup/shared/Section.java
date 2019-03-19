package de.dashup.shared;

import java.util.List;

public class Section implements DatabaseObject {
    private int section_id;
    private String section_name;

    private List<Panel> panels;

    public Section() {
    }

    public Section(int user_id, int section_id, String section_name) {
        this.section_id = section_id;
        this.section_name = section_name;
    }

    public void setId(int id) {
        this.section_id = id;
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

    @Override
    public int getId() {
        return section_id;
    }
}
