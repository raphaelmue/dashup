package de.dashup.shared;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LayoutModeSectionDTO implements DataTransferObject{

    private String sectionName;
    private String sectionId;
    private List<LayoutModeWidgetDTO> layoutModeWidgets;
    private int order;

    public LayoutModeSectionDTO() {
        super();
    }

    public LayoutModeSectionDTO(String sectionId, String sectionName, List<LayoutModeWidgetDTO> layoutModeWidgets, int order) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.layoutModeWidgets = layoutModeWidgets;
        this.order = order;
    }

    public List<Widget> getWidgets() {
        List<Widget> widgets = new ArrayList<>();
        for (LayoutModeWidgetDTO layoutModeWidgetDTO : layoutModeWidgets) {
            Widget widget = layoutModeWidgetDTO.toDataTransferObject();
        }
        return widgets;
    }

    public List<LayoutModeWidgetDTO> getLayoutModeWidgets()
    {
        return layoutModeWidgets;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public Section toDataTransferObject() {
        int sectionTechnicalId = Integer.valueOf(sectionId.substring(1));
        return new Section(sectionTechnicalId,sectionName,order);
    }

    public boolean isNewSection()
    {
        String sectionIdPrefix = sectionId.substring(0, 1);
        return "n".equals(sectionIdPrefix);

    }
}
