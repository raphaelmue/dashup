package de.dashup.shared;

public class Panel implements DatabaseObject {

    private int id;
    private String name, description;

    public Panel(int id, String name, String description) {
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
}
