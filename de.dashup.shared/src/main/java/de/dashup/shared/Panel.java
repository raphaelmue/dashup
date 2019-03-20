package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class Panel extends DatabasePanel {
    private String htmlContent;
    @SerializedName("panel_predecessor")
    private int predecessor;

    public Panel(int id, String name, String description, int numberOfDownloads, int averageRating,
                 int predecessor) {
        super(id, name, description, numberOfDownloads, averageRating);
        this.predecessor = predecessor;
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

    public int getPredecessor() {
        return predecessor;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
