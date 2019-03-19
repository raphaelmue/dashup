package de.dashup.shared;

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

        @Override
        public String toString() {
            return name;
        }
    }

    private String htmlContent;
    private Size size;

    public Panel() {
    }

    public Panel(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabasePanel) {
            this.setId(databaseObject.getId());
            this.setName(((DatabasePanel) databaseObject).getName());
            this.setDescription(((DatabasePanel) databaseObject).getDescription());
        }
        return this;
    }

    public Size getSize() {
        return size;
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
}
