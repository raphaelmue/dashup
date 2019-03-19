package de.dashup.application.controllers;
import de.dashup.shared.DashupSectionStructure;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DashupPanelStructure;
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

                Map<String, String> layout = DashupService.getInstance().loadLayout(user);
                for (Map.Entry<String, String> entry : layout.entrySet()) {
                    model.addAttribute(entry.getKey(), entry.getValue());
                }

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

    @PostMapping(value = "/handleLayout", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<Object> handleLayout(
            @CookieValue(name = "token", required = false) String token,
            @RequestBody DashupPanelStructure dps,
            HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException {
        User user = (User) this.localStorage.readObjectFromSession(request, "user");
        if(user == null)return new ResponseEntity(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);



        for (int i =0;i< dps.getSections().size();i++) {
            if(dps.getSections().get(i).getSection_id().contains("sn"))
            {
                if(i==0){
                    DashupService.getInstance().addSection(user, dps.getSections().get(i).getSection_name(), -1, -1);
                }else {
                    DashupService.getInstance().addSection(user, dps.getSections().get(i).getSection_name(), Integer.valueOf(dps.getSections().get(i - 1).getSection_id().substring(1)), -1);
                }
            }
            else
            {
                if(dps.getSections().get(i).getSection_order()==-10)
                {
                    DashupService.getInstance().deleteSection(user,Integer.valueOf(dps.getSections().get(i).getSection_id().substring(1)));
                }
                else
                {
                    String section_name = dps.getSections().get(i).getSection_name();
                    int section_id = Integer.valueOf(dps.getSections().get(i).getSection_id().substring(1));
                    if(i==0) {
                        DashupService.getInstance().updateSection(user, section_name, section_id, -1, -1);
                    }else {
                        DashupService.getInstance().updateSection(user, section_name, section_id, Integer.valueOf(dps.getSections().get(i - 1).getSection_id().substring(1)), -1);
                    }

                }
            }
        }

        JSONObject entity = new JSONObject();
        entity.put("message", "Success");
        return new ResponseEntity(entity, HttpStatus.OK);

    }




}


