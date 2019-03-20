package de.dashup.application.controllers.util;

import de.dashup.application.local.LocalStorage;
import de.dashup.application.local.format.I18N;
import de.dashup.shared.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;

public class ControllerHelper {

    public static String defaultMapping(String token, HttpServletRequest request, Model model, String viewName, ControllerAction<User> action) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            model.addAttribute("language", user.getSettings().getLanguage().getDisplayLanguage());
            model.addAttribute("theme", user.getSettings().getTheme().getTechnicalName());
            action.action(user);
            return viewName;
        }
        return "redirect:/login";
    }

    public static void setLocale(HttpServletRequest request, Locale locale) {
        if (locale != null) {
            LocalStorage.getInstance().writeObjectToSession(request, "language", I18N.Language.getLanguageByLocale(locale));
        }
        I18N.setLanguage((I18N.Language) LocalStorage.getInstance().readObjectFromSession(request, "language"));
    }
}
