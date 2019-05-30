package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Locale;

@Controller
@RequestMapping(value = "/")
public class EntryController {
    private final LocalStorage localStorage = LocalStorage.getInstance();

    @GetMapping(value = "/login")
    public String login(@RequestParam(name = "lang", required = false) Locale locale,
                        Model model, HttpServletRequest request) {
        ControllerHelper.setLocale(request, locale);
        model.addAttribute("theme", "blue-sky");
        return "login";
    }

    @PostMapping(value = "/handleLogin")
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
            if (user.getSettings() != null) {
                this.localStorage.writeCookie(response, "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE",
                        user.getSettings().getLanguage().toLanguageTag());
            }
            return "redirect:/";
        } else {
            return "redirect:/login/#invalidCredentials";
        }
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("theme", "blue-sky");
        return "register";
    }

    @PostMapping(value = "/handleRegisterUser")
    public String handleRegisterUser(@RequestParam(name = "email") String email, @RequestParam(name = "userName") String userName,
                                     @RequestParam(name = "password") String password,
                                     @RequestParam(name = "lang", required = false) Locale locale,
                                     HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = DashupService.getInstance().registerUser(email, userName, password);
        if (user != null) {
            DashupService.getInstance().updateSettings(user, true);
            this.localStorage.writeObjectToSession(request, "user", user);
            return "redirect:/";
        }
        return "redirect:/register/#emailInUse";
    }
}