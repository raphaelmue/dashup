package de.dashup.shared;

import java.util.List;

public class LayoutModeSection implements DataTransferObject{

    private String sectionName;
    private String sectionId;
    private List<LayoutModePanel> panelStructure;
    private int order;

    public LayoutModeSection() {
        super();
    }

    public LayoutModeSection(String sectionId, String sectionName, List<LayoutModePanel> panelStructure, int order) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.panelStructure = panelStructure;
        this.order = order;
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

    @Override
    public Section toDataTransferObject() {
        int sectionTechnicalId = Integer.valueOf(sectionId.substring(1));
        return new Section(sectionTechnicalId,sectionName,order);
    }

    public boolean isNewSection()
    {
        String sectionIdPrefix = sectionId.substring(0, 1);
        if(sectionIdPrefix.equals("n")){
            return true;
        }

        return false;
    }
}
