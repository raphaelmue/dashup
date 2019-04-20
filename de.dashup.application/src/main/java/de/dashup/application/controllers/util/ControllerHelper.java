package de.dashup.application.controllers.util;

import de.dashup.application.local.LocalStorage;
import de.dashup.application.local.format.I18N;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;

public class ControllerHelper {

    public static String defaultMapping(String token, HttpServletRequest request, Model model, String viewName, ControllerAction<DatabaseUser> action) throws SQLException {
        DatabaseUser databaseUser = LocalStorage.getInstance().getUser(request, token);
        DatabaseUser user = DashupService.getInstance().getUserById(databaseUser.getID());

        if (databaseUser != null) {
            model.addAttribute("language", user.getLanguage());
            model.addAttribute("theme", user.getTheme());
            model.addAttribute("background_image",  user.getBackgroundImage());
            action.action(databaseUser);
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
