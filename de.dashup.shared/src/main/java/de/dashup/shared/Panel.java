package de.dashup.shared;

public class Panel extends DatabasePanel {
    private String htmlContent;
    private int panel_predecessor,panel_successor;

    public Panel(int id, String name, String description, int number_of_downloads, int average_rating,
                 int panel_predecessor, int panel_successor) {
        super(id, name, description, number_of_downloads,average_rating);
        this.panel_predecessor = panel_predecessor;
        this.panel_successor = panel_successor;
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

    public int getPanel_predecessor() {
        return panel_predecessor;
    }

    public int getPanel_successor() {
        return panel_successor;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
