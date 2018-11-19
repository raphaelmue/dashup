package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.application.local.format.I18N;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Locale;

@Controller
@RequestMapping("/entry")
public class EntryController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(name = "invalidCredentials", required = false, defaultValue = "false") boolean invalidCredentials,
                        @RequestParam(name = "lang", required = false) Locale locale,
                        Model model, HttpServletRequest request) {
        ControllerHelper.setLocale(request, locale);

        if (invalidCredentials) {
            model.addAttribute("errorMessage", "<p>" + I18N.get("i18n.invalidCredentials") + "</p>");
        }
        return "login";
    }

    @RequestMapping(value = "/handleLogin", method = RequestMethod.POST)
    public String handleLogin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password, HttpServletRequest request) throws SQLException {
        User user = DashupService.getInstance().checkCredentials(email, password);
        if (user != null) {
            LocalStorage.writeObject(request, "user", user);
            return "redirect:/";
        } else {
            return "redirect:/entry/login?invalidCredentials=true";
        }
    }
}