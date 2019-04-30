package de.dashup.application.local.format;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

/**
 * I18N utility class.
 */
public final class I18N {

    private static Language language;

    public enum Language implements Serializable {
        ENGLISH("English", Locale.ENGLISH),
        GERMAN("Deutsch", Locale.GERMAN);

        private static final long serialVersionUID = -2204664357700920467L;

        private final String name;
        private final Locale locale;

        Language(String name, Locale locale) {
            this.name = name;
            this.locale = locale;
        }

        public String getName() {
            return name;
        }

        public Locale getLocale() {
            return locale;
        }

        public static Language getLanguageByLocale(Locale locale) {
            for (Language language : values()) {
                if (language.getLocale().equals(locale)) {
                    return language;
                }
            }
            return null;
        }

        public static Language getLanguageByName(String name) {
            for (Language language : values()) {
                if (language.getName().equals(name)) {
                    return language;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static Language getLocale() {
        return Objects.requireNonNullElse(language, Language.ENGLISH);
    }

    public static void setLanguage(Language language) {
        I18N.language = language;
    }

    /**
     * gets the string with the given key from the resource bundle for the current locale and uses it as first argument
     * to MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key  message key
     * @param args optional arguments for the message
     * @return localized formatted string
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n", getLocale().getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }
}