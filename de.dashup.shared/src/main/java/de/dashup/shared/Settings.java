package de.dashup.shared;

import java.util.Locale;

public class Settings {
    public enum Theme {
        BLUE_SKY("blue-sky", "Blue Sky"),
        BLACK_AND_WHITE("black-and-white", "Black And White"),
        HIGH_CONTRAST("high-contrast", "High Contrast");

        private final String styleSheetName, name;

        Theme(String styleSheetName, String name) {
            this.styleSheetName = styleSheetName;
            this.name = name;
        }

        public String getStyleSheetName() {
            return styleSheetName;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Locale language = Locale.ENGLISH;
    private Theme theme = Theme.BLUE_SKY;

    public Locale getLanguage() {
        return language;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
