package de.dashup.shared;

import java.io.Serializable;

public class DatabaseComponent implements Serializable, DatabaseObject {
    private static final long serialVersionUID = -8352045621135035810L;

    private int id;
    private String name;

    public DatabaseComponent() {
    }

    public DatabaseComponent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
