package de.dashup.shared.layout;

import java.util.List;

@SuppressWarnings("unused")
public class LayoutModeStructureDTO {

    private List<LayoutModeSectionDTO> sectionWidgetOrder;
    private List<LayoutModeSectionDTO> sectionsToDelete;

    public LayoutModeStructureDTO() {
        super();
    }

    public LayoutModeStructureDTO(List<LayoutModeSectionDTO> sectionWidgetOrder, List<LayoutModeSectionDTO> sectionsToDelete) {
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
