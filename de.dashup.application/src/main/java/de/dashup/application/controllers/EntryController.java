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
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Locale;

@Controller
@RequestMapping("/entry")
public class EntryController {
    private final LocalStorage localStorage = LocalStorage.getInstance();

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
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "rememberMe", defaultValue = "false") boolean rememberMe,
                              HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = DashupService.getInstance().checkCredentials(email, password, rememberMe);
        if (user != null) {
            if (rememberMe) {
                this.localStorage.writeCookie(response, "token", user.getToken());
            }
            this.localStorage.writeObjectToSession(request, "user", user);
            return "redirect:/";
        } else {
            return "redirect:/entry/login?invalidCredentials=true";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/handleRegisterUser", method = RequestMethod.POST)
    public String handleRegisterUser(@RequestParam(name = "email") String email, @RequestParam(name = "name") String name,
                                     @RequestParam(name = "surname") String surname, @RequestParam(name = "password") String password,
                                     @RequestParam(name = "repeat-password") String repeatPassword,
                                     @RequestParam(name = "lang", required = false) Locale locale,
                                     Model model, HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);

        if (password.equals(repeatPassword)) {
            User user = DashupService.getInstance().registerUser(email, name, surname, password);
            if (user != null) {
                DashupService.getInstance().changeLayout(user,"#ffffff", "", 20, "#000000", "Calibri", true);
                this.localStorage.writeObjectToSession(request, "user", user);
                return "redirect:/";
            }
            model.addAttribute("errorMessage", "<p>" + I18N.get("i18n.emailIsAlreadyRegistered") + "</p>");
            return "register";
        }
        model.addAttribute("errorMessage", "<p>" + I18N.get("i18n.passwordsNotMatching") + "</p>");
        return "register";
    }
}