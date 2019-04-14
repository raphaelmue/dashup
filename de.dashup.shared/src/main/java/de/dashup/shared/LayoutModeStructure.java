package de.dashup.shared;

import java.util.List;

public class LayoutModeStructure {

    List<String> panelsToDelete;
    List<LayoutModeSection> sectionPanelOrder;
    List<LayoutModeSection> sectionsToDelete;


    public LayoutModeStructure() {
        super();
    }

    public LayoutModeStructure(List<String> panelsToDelete, List<LayoutModeSection> sectionPanelOrder, List<LayoutModeSection> sectionsToDelete) {
        this.panelsToDelete = panelsToDelete;
        this.sectionPanelOrder = sectionPanelOrder;
        this.sectionsToDelete = sectionsToDelete;
    }

    public List<LayoutModeSection> getSectionPanelOrder() {
        return sectionPanelOrder;
    }

    public void setSectionPanelOrder(List<LayoutModeSection> sectionPanelOrder) {
        this.sectionPanelOrder = sectionPanelOrder;
    }

    public List<String> getPanelsToDelete() {
        return panelsToDelete;
    }

    public void setPanelsToDelete(List<String> panelsToDelete) {
        this.panelsToDelete = panelsToDelete;
    }

    public List<LayoutModeSection> getSectionsToDelete() {
        return sectionsToDelete;
    }

    public void setSectionsToDelete(List<LayoutModeSection> sectionsToDelete) {
        this.sectionsToDelete = sectionsToDelete;
    }
}
