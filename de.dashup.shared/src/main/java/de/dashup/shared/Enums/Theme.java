package de.dashup.shared.Enums;

public enum Theme {

    BLUE_SKY("Blue Sky"),
    GREEN_NATURE("Green Nature"),
    RED_LOVE("Red Love"),
    WHITE_DIAMOND("White Diamond"),
    BLACK_NIGHT("Black Night");

    private final String name;

    Theme( String name) {
        this.name = name;
    }

    public static Theme getThemeByName(String name) {
        for (Theme theme : values()) {
            if (theme.getName().equals(name)) {
                return theme;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

}