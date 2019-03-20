package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DashupPanelStructure;
import de.dashup.shared.DashupSectionStructure;
import de.dashup.shared.User;
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
import java.util.Map;

@Controller
@RequestMapping("/layoutMode")
public class LayoutModeController {

    private final LocalStorage localStorage = LocalStorage.getInstance();

    @RequestMapping(value = "/")
    public String login(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "layoutMode", user -> {
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("content", DashupBuilder.buildUsersPanelsLayoutMode(user));

            Map<String, String> layout = DashupService.getInstance().loadLayout(user);
            for (Map.Entry<String, String> entry : layout.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        });
    }

    @RequestMapping(value = "/confirmChanges", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(@RequestBody DashupPanelStructure body, Model model, HttpServletRequest request) {
        System.out.println(body);
        return "layoutMode";
    }

    @PostMapping(value = "/handleLayout", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<Object> handleLayout(
            @CookieValue(name = "token", required = false) String token,
            @RequestBody DashupPanelStructure dps,
            HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException {
        User user = (User) this.localStorage.readObjectFromSession(request, "user");
        if (user == null) return new ResponseEntity(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);


        for (DashupSectionStructure dss : dps.getSections()) {
            if (dss.getSection_id().contains("sn")) {
                DashupService.getInstance().addSection(user, dss.getSection_name(), dss.getSection_order());
            } else {
                if (dss.getSection_order() == -10) {
                    DashupService.getInstance().deleteSection(user, Integer.valueOf(dss.getSection_id().substring(1)));
                } else {
                    String section_name = dss.getSection_name();
                    int section_id = Integer.valueOf(dss.getSection_id().substring(1));
                    DashupService.getInstance().updateSection(user, section_name, section_id, dss.getSection_order());

                }
            }
        }

        JSONObject entity = new JSONObject();
        entity.put("message", "Success");
        return new ResponseEntity(entity, HttpStatus.OK);
    }
}


