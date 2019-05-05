package de.dashup.shared;

import java.util.List;

@SuppressWarnings("unused")
public class LayoutModeSectionDTO implements DataTransferObject{

    private String sectionName;
    private String sectionId;
    private List<LayoutModeWidgetDTO> widgetStructure;
    private int order;

    public LayoutModeSectionDTO() {
        super();
    }

    public LayoutModeSectionDTO(String sectionId, String sectionName, List<LayoutModeWidgetDTO> widgetStructure, int order) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.widgetStructure = widgetStructure;
        this.order = order;
    }

    public List<LayoutModeWidgetDTO> getWidgetStructure() {
        return widgetStructure;
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
        return sectionIdPrefix.equals("n");

    }
}
