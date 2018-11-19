package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.application.local.format.I18N;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

class ControllerHelper {
    static void setLocale(HttpServletRequest request, Locale locale) {
        if (locale != null) {
            LocalStorage.writeObject(request, "language", I18N.Language.getLanguageByLocale(locale));
        }
        I18N.setLanguage((I18N.Language) LocalStorage.readObject(request, "language"));
    }
}
