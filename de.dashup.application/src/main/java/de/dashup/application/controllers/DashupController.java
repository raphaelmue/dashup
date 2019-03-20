package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DashupController {
    private final LocalStorage localStorage = LocalStorage.getInstance();
    @RequestMapping("/")
    public String main(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "index", user -> {
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());

            Map<String, String> layout = DashupService.getInstance().loadLayout(user);
            for (Map.Entry<String, String> entry : layout.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }

            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("content", DashupBuilder.buildUsersPanels(user));
        });
    }

    @RequestMapping("/handleLogout")
    public String handleLogout(@CookieValue(name = "token", required = false) String token,
                               HttpServletRequest request, HttpServletResponse response) {
        this.localStorage.writeObjectToSession(request, "user", null);
        if (token != null && !token.isEmpty()) {
            try {
                DashupService.getInstance().deleteToken(token);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.localStorage.deleteCookie(response, "token");

        return "redirect:/login";
    }

    @RequestMapping("/layoutmode")
    public String layoutMode() {
        System.out.println("Delegating to the layoutMode controller");
        return "redirect:/layoutmode/layoutMode";
    }
}
