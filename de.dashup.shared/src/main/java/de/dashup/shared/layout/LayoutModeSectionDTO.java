package de.dashup.shared.layout;

import de.dashup.shared.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class LayoutModeSectionDTO implements DataTransferObject {

    private String sectionName;
    private String sectionId;
    private List<LayoutModeWidgetDTO> layoutModeWidgets;
    private int index;

    public LayoutModeSectionDTO() {
        super();
    }

    public LayoutModeSectionDTO(String sectionId, String sectionName, List<LayoutModeWidgetDTO> layoutModeWidgets) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.layoutModeWidgets = layoutModeWidgets;
    }

    public List<Widget> getWidgets() {
        List<Widget> widgets = new ArrayList<>();
        for (LayoutModeWidgetDTO layoutModeWidgetDTO : layoutModeWidgets) {
            Widget widget = layoutModeWidgetDTO.toDataTransferObject();
            widgets.add(widget);
        }
        return widgets;
    }

    public List<LayoutModeWidgetDTO> getLayoutModeWidgets() {
        return layoutModeWidgets;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Section toDataTransferObject() {
        int sectionTechnicalId = Integer.parseInt(sectionId.substring(1));
        return new Section(sectionTechnicalId, sectionName, index);
    }

    public boolean isNewSection() {
        String sectionIdPrefix = sectionId.substring(0, 1);
        return "n".equals(sectionIdPrefix);

    }
}
