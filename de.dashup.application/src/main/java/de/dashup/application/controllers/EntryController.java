package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseUser;
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
@RequestMapping("/")
public class EntryController {
    private final LocalStorage localStorage = LocalStorage.getInstance();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(name = "lang", required = false) Locale locale,
                        Model model, HttpServletRequest request) {
        ControllerHelper.setLocale(request, locale);
        model.addAttribute("theme", "blue-sky");
        return "login";
    }

    @RequestMapping(value = "/handleLogin", method = RequestMethod.POST)
    public String handleLogin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "rememberMe", defaultValue = "false") boolean rememberMe,
                              HttpServletRequest request, HttpServletResponse response) throws SQLException {
        DatabaseUser user = DashupService.getInstance().checkCredentials(email, password, rememberMe);
        if (user != null) {
            if (rememberMe) {
                this.localStorage.writeCookie(response, "token", DashupService.getInstance().getTokenByUser(user).getToken());
            }
            this.localStorage.writeObjectToSession(request, "user", user);
            this.localStorage.writeCookie(response, "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", user.getLanguage());
            return "redirect:/";
        } else {
            return "redirect:/login/#invalidCredentials";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("theme", "blue-sky");
        return "register";
    }

    @RequestMapping(value = "/handleRegisterUser", method = RequestMethod.POST)
    public String handleRegisterUser(@RequestParam(name = "email") String email, @RequestParam(name = "userName") String userName,
                                     @RequestParam(name = "password") String password,
                                     @RequestParam(name = "lang", required = false) Locale locale,
                                     HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        DatabaseUser user = DashupService.getInstance().registerUser(email, userName, password);
        if (user != null) {
            this.localStorage.writeObjectToSession(request, "user", user);
            return "redirect:/";
        }
        return "redirect:/register/#emailInUse";
    }
}