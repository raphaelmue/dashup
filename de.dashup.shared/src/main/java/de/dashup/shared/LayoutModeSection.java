package de.dashup.shared;

import java.util.List;

public class LayoutModeSection {

    private String sectionName;
    private String sectionId;
    private List<LayoutModePanel> panelStructure;

    public LayoutModeSection() {
        super();
    }

    public LayoutModeSection(String sectionId, String sectionName, List<LayoutModePanel> panelStructure) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.panelStructure = panelStructure;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public List<LayoutModePanel> getPanelStructure() {
        return panelStructure;
    }
}
