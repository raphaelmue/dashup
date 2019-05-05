package de.dashup.shared;

@SuppressWarnings("unused")
public class LayoutModeWidgetDTO implements DataTransferObject {

    private String widgetId;
    private String widgetSize;
    private int order;

    public LayoutModeWidgetDTO() {
        super();
    }

    public LayoutModeWidgetDTO(String widgetId, String widgetSize, int order) {
        this.widgetId = widgetId;
        this.widgetSize = widgetSize;
        this.order = order;
    }

    public String getWidgetSize() {
        return widgetSize;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public Widget toDataTransferObject() {

        String widgetFrontendId = widgetId.substring(1);
        int widgetTechnicalId = Integer.valueOf(widgetFrontendId);
        return new Widget(widgetTechnicalId,"","",0,0,order);

    }
}
