package de.dashup.shared;

import java.util.List;

public class DashupSectionStructure {

    private List<DashupPanelInformation> panels;
    private String section_id,section_name;
    private int section_order;

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public int getSection_order() {
        return section_order;
    }

    public void setSection_order(int section_order) {
        this.section_order = section_order;
    }

    public List<DashupPanelInformation> getPanels() {
        return panels;
    }

    public void setPanels(List<DashupPanelInformation> panels) {
        this.panels = panels;
    }
}
