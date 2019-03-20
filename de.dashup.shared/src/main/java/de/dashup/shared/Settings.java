package de.dashup.shared;

import java.util.Locale;

public class Settings {
    public enum Theme {
        BLUE_SKY("blue-sky", "Blue Sky"),
        BLACK_AND_WHITE("black-and-white", "Black And White"),
        HIGH_CONTRAST("high-contrast", "High Contrast");

        private final String technicalName, name;

        Theme(String technicalName, String name) {
            this.technicalName = technicalName;
            this.name = name;
        }

        public String getTechnicalName() {
            return technicalName;
        }

        public String getName() {
            return name;
        }

        public static Theme getThemeByTechnicalName(String technicalName) {
            for (Theme theme : values()) {
                if (theme.getTechnicalName().equals(technicalName)) {
                    return theme;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Locale language = Locale.ENGLISH;
    private Theme theme = Theme.BLUE_SKY;
    private String backgroundImage;

    public Locale getLanguage() {
        return language;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
