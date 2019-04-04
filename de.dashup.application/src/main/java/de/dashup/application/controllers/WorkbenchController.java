package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/workbench")
public class WorkbenchController {

    @RequestMapping("/")
    public String settings(@CookieValue(name = "token", required = false) String token,
                           HttpServletRequest request, Model model) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "workbench", user -> {
            String[] drafts = {"One Widget", "And another one", "All good things.. "};
            model.addAttribute("drafts", drafts);
        });
    }
}
