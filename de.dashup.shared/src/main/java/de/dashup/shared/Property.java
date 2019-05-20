package de.dashup.shared;

public class Property extends DatabaseProperty {

    public enum Type {
        TEXT("text"),
        NUMBER("number"),
        PASSWORD("password");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type getTypeByName(String name) {
            for (Type type : values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            return null;
        }
    }

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

    public Type getTypeObject() {
        return Type.getTypeByName(super.getType());
    }
}
