package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.models.DatabaseUser;
import de.dashup.shared.Layout;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping("/layoutMode")
public class LayoutModeController {

    private final LocalStorage localStorage = LocalStorage.getInstance();

    @RequestMapping(value = "/")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "layoutMode", databaseUser -> {
            DatabaseUser user = DashupService.getInstance().getUserById(databaseUser.getID());
            model.addAttribute("name", databaseUser.getName());
            model.addAttribute("email", databaseUser.getEmail());
            model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));

            model.addAttribute("background_image", user.getBackgroundImage());
        });
    }

    @RequestMapping(value = "/confirmChanges", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(@RequestBody Layout body, Model model, HttpServletRequest request) {
        System.out.println(body);
        return "layoutMode";
    }

    @PostMapping(value = "/handleLayout", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<Object> handleLayout(
            @CookieValue(name = "token", required = false) String token,
            @RequestBody Layout layout,
            HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException {
        DatabaseUser user = (DatabaseUser) this.localStorage.readObjectFromSession(request, "user");
        if (user == null){
            return new ResponseEntity(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }

        //To be implemented according to new data model

        JSONObject entity = new JSONObject();
        entity.put("message", "Success");
        return new ResponseEntity(entity, HttpStatus.OK);
    }
}


