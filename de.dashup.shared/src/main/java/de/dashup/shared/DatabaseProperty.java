package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class DatabaseProperty implements DatabaseObject {
    private int id;
    private String property;
    private String name;
    private String type;
    @SerializedName("default_value")
    private String defaultValue;
    private String value;

    public DatabaseProperty(int id, String property, String name, String type, String defaultValue, String value) {
        this.id = id;
        this.property = property;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
