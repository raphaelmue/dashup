package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.layoutmode.ChangeHandler;
import de.dashup.model.service.DashupService;
import de.dashup.shared.*;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/layoutMode")
public class LayoutModeController {

    @RequestMapping(value = "/")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "layoutMode", user -> {
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));
        });
    }

    @PostMapping(value = "/handleSaveChanges", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleSaveChanges(@CookieValue(name = "token", required = false) String token,
                                                    @RequestBody LayoutModeStructure layoutModeStructure,
                                                    Locale locale,
                                                    HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = LocalStorage.getInstance().getUser(request, token);
        //ChangeHandler.getInstance(layoutModeStructure, user).processLayoutModeChanges();

        JSONObject entity = new JSONObject();
        entity.put("message", "Success");
        return new ResponseEntity<>("{\"message\":\"success\"}", HttpStatus.OK);

    }
}


