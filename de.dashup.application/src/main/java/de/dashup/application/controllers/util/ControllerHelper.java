package de.dashup.application.controllers.util;

import de.dashup.application.local.LocalStorage;
import de.dashup.application.local.format.I18N;
import de.dashup.shared.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Logger;

public class ControllerHelper {

    /**
     * Literals
     */
    private static final String LANGUAGE = "language";


    private static final Logger logger = Logger.getLogger("Dashup");

    private ControllerHelper() {
        super();
    }

    public static String defaultMapping(String token, HttpServletRequest request, Model model, String viewName, ControllerAction<User> action) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            model.addAttribute(LANGUAGE, user.getSettings().getLanguage().toLanguageTag());
            model.addAttribute("theme", user.getSettings().getTheme().getTechnicalName());
            action.action(user);
            return viewName;
        }
        return "redirect:/login";
    }

    public static void setLocale(HttpServletRequest request, Locale locale) {
        if (locale != null) {
            LocalStorage.getInstance().writeObjectToSession(request, LANGUAGE, I18N.Language.getLanguageByLocale(locale));
        }
        I18N.setLanguage((I18N.Language) LocalStorage.getInstance().readObjectFromSession(request, LANGUAGE));
    }

    public static Logger getLogger() {
        return logger;
    }
}
