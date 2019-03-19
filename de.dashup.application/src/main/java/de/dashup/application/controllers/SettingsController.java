package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Settings;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
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
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            model.addAttribute("name", user.getName());
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("email", user.getEmail());
            if (user.getSettings() != null) {
                model.addAttribute("language", user.getSettings().getLanguage().getDisplayLanguage());
            } else {
                model.addAttribute("language", "English");
            }
            return "settings";
        }
        return "redirect:/welcome";
    }

    @RequestMapping("/changeLanguage")
    public String handleChangeLanguage(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(value = "lang") String lang,
                                       HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Settings settings = new Settings();
            settings.setLanguage(Locale.forLanguageTag(lang));
            user.setSettings(settings);
            DashupService.getInstance().updateSettings(user);
        }
        return "redirect:/profile/";
    }

    @RequestMapping("/changePassword")
    public String handleChangePassword(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(name = "oldPassword") String oldPassword,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                DashupService.getInstance().updatePassword(user, oldPassword, newPassword);
            } catch (IllegalArgumentException ignored) {
            }
            return "redirect:/profile/";
        }
        return "redirect:/welcome";
    }
}