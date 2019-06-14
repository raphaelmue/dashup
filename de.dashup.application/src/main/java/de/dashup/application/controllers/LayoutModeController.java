package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.layout.LayoutModeStructureDTO;
import de.dashup.shared.User;
import de.dashup.shared.layout.Section;
import de.dashup.shared.layout.Widget;
import de.dashup.shared.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/layoutMode")
public class LayoutModeController {

    @GetMapping(value = "/")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "layoutMode", user -> {
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));
            model.addAttribute("small", Widget.Size.SMALL.getStyleClass());
            model.addAttribute("medium", Widget.Size.MEDIUM.getStyleClass());
            model.addAttribute("large", Widget.Size.LARGE.getStyleClass());
            DashupService.getInstance().getSectionsAndPanels(user);
            List<Widget> widgets = new ArrayList<>();
            for (Section section : user.getSections()) {
                if (section.getWidgets() != null) {
                    widgets.addAll(section.getWidgets());
                }
            }
            model.addAttribute("widgets", widgets);
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

    @PostMapping(value = "/updateProperties")
    public String handleUpdateProperties(@CookieValue(name = "token", required = false) String token,
                                         @RequestParam(value = "widgetId") int widgetId,
                                         @RequestParam(value = "properties") String jsonProperties,
                                         HttpServletRequest request) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget widget = new Widget();
            widget.setId(widgetId);
            JSONArray jsonArray = new JSONArray(URLDecoder.decode(jsonProperties, StandardCharsets.UTF_8.name()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                widget.getProperties().put(jsonObject.getString("property"),
                        new Property(jsonObject.getInt("id"), jsonObject.getString("property"),
                                null, null, null, jsonObject.getString("value")));
            }

            DashupService.getInstance().updateUsersWidgetProperties(user, widget);
            return "redirect:/layoutMode/";
        }
        return "redirect:/login";
    }
}


