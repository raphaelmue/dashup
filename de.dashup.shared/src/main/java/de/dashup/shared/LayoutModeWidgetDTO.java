package de.dashup.shared;

public class LayoutModePanel implements DataTransferObject {

    private String panelId;
    private String panelSize;
    private int order;

    public LayoutModePanel() {
        super();
    }

    public LayoutModePanel(String panelId, String panelSize, int order) {
        this.panelId = panelId;
        this.panelSize = panelSize;
        this.order = order;
    }

    public String getPanelId() {
        return panelId;
    }

    public String getPanelSize() {
        return panelSize;
    }

    @Override
    public DatabaseObject toDataTransferObject() {

        String widgetFrontendId = panelId.substring(1);
        int widgetTechnicalId = Integer.valueOf(widgetFrontendId);
        return new Widget(widgetTechnicalId,"","",0,0,order);

    }
}
