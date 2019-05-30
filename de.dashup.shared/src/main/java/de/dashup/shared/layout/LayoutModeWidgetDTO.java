package de.dashup.shared.layout;

import de.dashup.shared.DataTransferObject;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class LayoutModeWidgetDTO implements DataTransferObject {

    private String widgetId;
    private String widgetSize;
    private int index;

    public LayoutModeWidgetDTO() {
        super();
    }

    public LayoutModeWidgetDTO(String widgetId, String widgetSize) {
        this.widgetId = widgetId;
        this.widgetSize = widgetSize;
    }

    public String getWidgetSize() {
        return widgetSize;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Widget toDataTransferObject() {

        String widgetFrontendId = widgetId.substring(1);
        int widgetTechnicalId = Integer.valueOf(widgetFrontendId);
        return new Widget(widgetTechnicalId, "", "", 0, 0, index, 0, LocalDate.now(), "");

    }
}