package de.dashup.shared;

import java.util.List;

@SuppressWarnings("unused")
public class LayoutModeStructure {

    private List<LayoutModeSectionDTO> sectionWidgetOrder;
    private List<LayoutModeSectionDTO> sectionsToDelete;


    public LayoutModeStructure() {
        super();
    }

    public LayoutModeStructure(List<LayoutModeSectionDTO> sectionWidgetOrder, List<LayoutModeSectionDTO> sectionsToDelete) {
        this.sectionWidgetOrder = sectionWidgetOrder;
        this.sectionsToDelete = sectionsToDelete;
    }

    public List<LayoutModeSectionDTO> getSectionWidgetOrder() {
        return sectionWidgetOrder;
    }

    public List<LayoutModeSectionDTO> getSectionsToDelete() {
        return sectionsToDelete;
    }


}
