package de.dashup.shared;

public class LayoutModePanel {

    private String panelId;
    private String panelSize;

    public LayoutModePanel() {
        super();
    }

    public LayoutModePanel(String panelId, String panelSize) {
        this.panelId = panelId;
        this.panelSize = panelSize;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getPanelSize() {
        return panelSize;
    }

    public void setPanelSize(String panelSize) {
        this.panelSize = panelSize;
    }
}
