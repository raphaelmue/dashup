package de.dashup.shared;

public class Panel extends DatabasePanel {
    private String htmlContent;
    private int panel_order;

    public Panel(int id, String name, String description, int panel_order) {
        super(id, name, description);
        this.panel_order=panel_order;
    }

    @Override
    public DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabasePanel) {
            this.setId(databaseObject.getId());
            this.setName(((DatabasePanel) databaseObject).getName());
            this.setDescription(((DatabasePanel) databaseObject).getDescription());
        }
        return null;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
