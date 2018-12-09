package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Layout;
import de.dashup.shared.User;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/rest")
public class RESTController {

    @PostMapping(value = "/handleLayout", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Object> handleLayout(
                                         @CookieValue(name = "token", required = false) String token,
                                         @RequestBody Layout layout,
                                         HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException, IOException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user == null)
            return new ResponseEntity(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);

        boolean success = DashupService.getInstance().changeLayout(user, layout.getBackgroundColor(), layout.getBackgroundImage(), Integer.parseInt(layout.getHeadingSize()),
                layout.getHeadingColor(), layout.getFontHeading(), layout.getFontText(), false);
        JSONObject entity = new JSONObject();
        if(success){
            entity.put("message", "Success");
            return new ResponseEntity(entity, HttpStatus.OK);
        } else {
            entity.put("message", "Failure");
            return new ResponseEntity(entity, HttpStatus.BAD_REQUEST);
        }

    }

}