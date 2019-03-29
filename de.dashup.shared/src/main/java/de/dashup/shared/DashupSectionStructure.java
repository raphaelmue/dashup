package de.dashup.shared;

import java.util.List;

public class DashupSectionStructure {

    private List<DashupPanelInformation> panels;
    private String section_id, section_name;
    private int section_order;

    public String getSectionId() {
        return section_id;
    }

    public void setSectionId(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSectionName(String section_name) {
        this.section_name = section_name;
    }

    public int getSectionOrder() {
        return section_order;
    }

    public void setSectionOrder(int section_order) {
        this.section_order = section_order;
    }

    public List<DashupPanelInformation> getPanels() {
        return panels;
    }

    public void setPanels(List<DashupPanelInformation> panels) {
        this.panels = panels;
    }
}
