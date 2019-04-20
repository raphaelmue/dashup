package de.dashup.shared;

import de.dashup.shared.Enums.Theme;

import java.util.Locale;

public class Settings {

    private Locale language;
    private Theme theme;
    private String backgroundImage;

    public Settings(Locale language, Theme theme, String backgroundImage) {
        this.language = language;
        this.theme = theme;
        this.backgroundImage = backgroundImage;
    }

    public Locale getLanguage() {
        return this.language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

}