package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseUser;
import de.dashup.shared.Enums.Theme;
import de.dashup.shared.Settings;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @RequestMapping("/")
    public String settings(@CookieValue(name = "token", required = false) String token,
                           HttpServletRequest request, Model model) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "settings", user -> {
            model.addAttribute("name", user.getName());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("fullName", user.getSurname() + " " + user.getName());
            model.addAttribute("email", user.getEmail());
        });
    }

    @RequestMapping("/changeLanguage")
    public String handleChangeLanguage(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(value = "lang") String lang,
                                       HttpServletRequest request) throws SQLException {
        DatabaseUser databaseUser = LocalStorage.getInstance().getUser(request, token);
        User user = DashupService.getInstance().getUserById(databaseUser.getID());
        if (user != null) {
            user.getSettings().setLanguage(Locale.forLanguageTag(lang));
            DashupService.getInstance().updateLanguage(user);
        }
        return "redirect:/settings/#changedLanguage";
    }

    @RequestMapping("/changePassword")
    public String handleChangePassword(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(name = "oldPassword") String oldPassword,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       HttpServletRequest request) throws SQLException {
        DatabaseUser databaseUser = LocalStorage.getInstance().getUser(request, token);
        if (databaseUser != null) {
            try {
                DashupService.getInstance().updatePassword(databaseUser, oldPassword, newPassword);
            } catch (IllegalArgumentException ignored) {
                return "redirect:/settings/#oldPasswordWrong";
            }
            return "redirect:/settings/#changedPassword";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/changeLayout", method = RequestMethod.POST)
    public String handleLayout(@CookieValue(name = "token", required = false) String token,
                               @RequestParam("theme") String theme,
                               @RequestParam("backgroundImage") String backgroundImage,
                               HttpServletRequest request) throws SQLException {
        DatabaseUser databaseUser = LocalStorage.getInstance().getUser(request, token);
        User user = DashupService.getInstance().getUserById(databaseUser.getID());
        if (user != null) {
            user.getSettings().setTheme(Theme.getThemeByName(theme));
            user.getSettings().setBackgroundImage(backgroundImage);
            DashupService.getInstance().updateSettings(user);
            return "redirect:/settings/#changedLayout";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/changePersonalInfo", method = RequestMethod.POST)
    public String handlePersonalInfo(@CookieValue(name = "token", required = false) String token,
                                     @RequestParam("name") String name,
                                     @RequestParam("surname") String surname,
                                     HttpServletRequest request) throws SQLException {
        DatabaseUser databaseUser = LocalStorage.getInstance().getUser(request, token);
        User user = DashupService.getInstance().getUserById(databaseUser.getID());
        if (databaseUser != null) {
            DashupService.getInstance().updateNameAndSurname(user, name, surname);
            return "redirect:/settings/#changedPersonalInfo";
        }
        return "redirect:/login";
    }
}