package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/layout")
public class LayoutController {

    @RequestMapping("/")
    public String layout(@CookieValue(name = "token", required = false) String token,
                       HttpServletRequest request, Model model) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {

            Map<String, String> layout = DashupService.getInstance().loadLayout(user);
            for (Map.Entry<String, String> entry : layout.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }

            return "layout";
        }
        return "redirect:/welcome";
    }

}