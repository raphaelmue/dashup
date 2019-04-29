package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class Panel extends DatabasePanel {

    public enum Size {
        SMALL("small", "m2 s6"),
        MEDIUM("medium", "m4 s12"),
        LARGE("large", "m6 s12");

        private final String name, styleClass;

        Size(String name, String styleClass) {
            this.name = name;
            this.styleClass = styleClass;
        }

        public String getName() {
            return name;
        }

        public String getStyleClass() {
            return styleClass;
        }

        public static Size getSizeByName(String name) {
            for (Size size : values()) {
                if (size.getName().equals(name)) {
                    return size;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private String htmlContent;
    private Size size;
    @SerializedName("panel_predecessor")
    private int predecessor;

    public Panel() {
    }

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
            this.setAverageRating(((DatabasePanel) databaseObject).getAverageRating());
            this.setNumberOfRatings(((DatabasePanel) databaseObject).getNumberOfRatings());
            this.setShortDescription(((DatabasePanel) databaseObject).getShortDescription());
            this.setPublicationDate(((DatabasePanel) databaseObject).getPublicationDate());
        }
        return this;
    }

    public Size getSize() {
        return size;
    }

    public int getPredecessor() {
        return predecessor;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }
}
