package de.dashup.shared;

public class Property extends DatabaseProperty {
    public Property(int id, String property, String name, String defaultValue, String value) {
        super(id, property, name, defaultValue, value);
    }

    @Override
    public String getValue() {
        if (super.getValue() != null) {
            return super.getValue();
        }
        return super.getDefaultValue();
    }
}
