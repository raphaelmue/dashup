package de.dashup.shared;

import java.io.Serializable;
import java.util.Locale;

public class Settings implements Serializable {
    private static final long serialVersionUID = -7111094553970271511L;

    public enum Theme {
        BLUE_SKY("blue-sky", "Blue Sky"),
        GREEN_NATURE("green-nature", "Green Nature"),
        RED_LOVE("red-love", "Red Love"),
        WHITE_DIAMOND("white-diamond", "White Diamond"),
        BLACK_NIGHT("black-night", "Black Night");

        private final String technicalName;
        private final String name;

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
    private String backgroundImage = "";

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
