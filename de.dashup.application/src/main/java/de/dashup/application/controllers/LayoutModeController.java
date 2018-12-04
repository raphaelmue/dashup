package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DashupPanelStructure;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/layoutmode")
public class LayoutModeController {

    private final LocalStorage localStorage = LocalStorage.getInstance();

    @RequestMapping(value = "/layoutMode")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        User user = (User) this.localStorage.readObjectFromSession(request, "user");
        if (user != null || token != null && !token.isEmpty()) {
            if (token != null && !token.isEmpty()) {
                try {
                    user = DashupService.getInstance().getUserByToken(token);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (user != null) {
                DashupService.getInstance().getSectionsAndPanels(user);
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));

            }
        }
        return "layoutMode";
    }

    @RequestMapping(value = "/confirmChanges", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(@RequestBody DashupPanelStructure body, Model model, HttpServletRequest request) {

        System.out.println(body);
        return "layoutMode";

    }



}


