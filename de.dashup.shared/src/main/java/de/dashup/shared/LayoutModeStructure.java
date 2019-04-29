package de.dashup.shared;

import java.util.List;

public class LayoutModeStructure {

    private List<String> panelsToDelete;
    private List<LayoutModeSection> sectionPanelOrder;
    private List<LayoutModeSection> sectionsToDelete;


    public LayoutModeStructure() {
        super();
    }

    public LayoutModeStructure(List<LayoutModeSection> sectionPanelOrder, List<LayoutModeSection> sectionsToDelete) {
        this.sectionPanelOrder = sectionPanelOrder;
        this.sectionsToDelete = sectionsToDelete;
    }

    public List<LayoutModeSection> getSectionPanelOrder() {
        return sectionPanelOrder;
    }

    public List<LayoutModeSection> getSectionsToDelete() {
        return sectionsToDelete;
    }


}
