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
                          HttpServletRequest request, Model model) {
        User user = (User) LocalStorage.getInstance().readObjectFromSession(request, "user");
        if (user != null || token != null && !token.isEmpty()) {
            if (token != null && !token.isEmpty()) {
                try {
                    user = DashupService.getInstance().getUserByToken(token);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

}