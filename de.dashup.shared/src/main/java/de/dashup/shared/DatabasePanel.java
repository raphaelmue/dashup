package de.dashup.shared;

public class DatabasePanel implements DatabaseObject {

    private int id;
    private String name, description;

    public DatabasePanel() {
    }

    DatabasePanel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
