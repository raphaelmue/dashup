package de.dashup.shared;

public class Property extends DatabaseProperty {
    public Property(int id, String property, String name, String type, String defaultValue, String value) {
        super(id, property, name, type, defaultValue, value);
    }

    @Override
    public String getValue() {
        if (super.getValue() != null) {
            return super.getValue();
        }
        return super.getDefaultValue();
    }
}
