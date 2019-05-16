package de.dashup.shared;

public class DatabaseProperty implements DatabaseObject {
    private int id;
    private String property;
    private String name;
    private String defaultValue;
    private String value;

    public DatabaseProperty(int id, String property, String name, String defaultValue, String value) {
        this.id = id;
        this.property = property;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
