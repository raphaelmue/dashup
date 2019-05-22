package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.LayoutModeStructureDTO;
import de.dashup.shared.User;
import de.dashup.shared.Widget;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;

@Controller
@RequestMapping(value = "/layoutMode")
public class LayoutModeController {

    @RequestMapping(value = "/")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "layoutMode", user -> {
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));
            model.addAttribute("small", Widget.Size.SMALL.getStyleClass());
            model.addAttribute("medium",  Widget.Size.MEDIUM.getStyleClass());
            model.addAttribute("large",  Widget.Size.LARGE.getStyleClass());
        });
    }

    @PostMapping(value = "/handleSaveChanges", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleSaveChanges(@CookieValue(name = "token", required = false) String token,
                                                    @RequestBody LayoutModeStructureDTO layoutModeStructureDTO,
                                                    Locale locale,
                                                    HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = LocalStorage.getInstance().getUser(request, token);
        DashupService.getInstance().processLayoutModeChanges(layoutModeStructureDTO, user);

        JSONObject entity = new JSONObject();
        entity.put("message", "Success");
        return new ResponseEntity<>("{\"message\":\"success\"}", HttpStatus.OK);

    }
}


