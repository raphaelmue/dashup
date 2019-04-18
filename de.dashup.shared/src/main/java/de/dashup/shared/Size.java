package de.dashup.shared;

public enum Size {
    SMALL("small", "s4 m4 l4 xl4"),
    MEDIUM("medium", "s6 m6 l6 xl6"),
    LARGE("large", "s8 m8 l8 xl8");

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