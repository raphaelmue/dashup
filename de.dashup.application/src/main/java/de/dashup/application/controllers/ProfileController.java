package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping("/")
    public String profile(@CookieValue(name = "token", required = false) String token,
                          HttpServletRequest request, Model model) throws SQLException {
        User user = (User) LocalStorage.getInstance().readObjectFromSession(request, "user");
        if (user != null || token != null && !token.isEmpty()) {
            if (token != null && !token.isEmpty()) {
                user = DashupService.getInstance().getUserByToken(token);
            }
            if (user != null) {
                model.addAttribute("name", user.getName());
                model.addAttribute("fullName", user.getFullName());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("language", "English");
                return "profile";
            }
        }
        return "redirect:/welcome";
    }

    @RequestMapping("/changeLanguage")
    public String handleChangeLanguage(@RequestParam(value = "lang") String lang) {
        return "redirect:/profile/";
    }

    @RequestMapping("/changePassword")
    public String handleChangePassword(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(name = "oldPassword") String oldPassword,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       HttpServletRequest request) throws SQLException {
        User user = (User) LocalStorage.getInstance().readObjectFromSession(request, "user");
        if (user != null || token != null && !token.isEmpty()) {
            if (token != null && !token.isEmpty()) {
                user = DashupService.getInstance().getUserByToken(token);
            }
            if (user != null) {
                 try {
                     DashupService.getInstance().changePassword(user, oldPassword, newPassword);
                 } catch(IllegalArgumentException ignored) { }
            }
        }
        return "redirect:/profile/";
    }
}