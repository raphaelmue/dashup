package de.dashup.shared.enums;

public enum Theme {

    BLUE_SKY("Blue Sky","blue-sky"),
    GREEN_NATURE("Green Nature", "green-nature"),
    RED_LOVE("Red Love", "red-love"),
    WHITE_DIAMOND("White Diamond", "white-diamond"),
    BLACK_NIGHT("Black Night", "black-night");

    private final String name;
    private final String technicalName;

    Theme(String name, String technicalName) {
        this.name = name;
        this.technicalName = technicalName;
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
        return this.name;
    }

    public String getTechnicalName() {
        return this.technicalName;
    }

    public String toString() {
        return this.name;
    }

}