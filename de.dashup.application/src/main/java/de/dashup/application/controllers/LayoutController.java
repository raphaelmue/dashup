package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/layout")
public class LayoutController {

    @RequestMapping("/")
    public String layout(@CookieValue(name = "token", required = false) String token,
                       HttpServletRequest request, Model model) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            return "layout";
        }
        return "redirect:/welcome";
    }

}