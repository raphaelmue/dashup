package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.exceptions.EmailAlreadyInUseException;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Settings;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

    @GetMapping(value = "/")
    public String settings(@CookieValue(name = "token", required = false) String token,
                           HttpServletRequest request, Model model) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "settings", user -> {
            model.addAttribute("name", user.getName());
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("birthDate", user.getBirthDate());
            model.addAttribute("company", user.getCompany());
            model.addAttribute("bio", user.getBio());
            model.addAttribute("backgroundImage", user.getSettings().getBackgroundImage());
        });
    }

    @PostMapping(value = "/changeEmail")
    public String handleChangeEmail(@CookieValue(name = "token", required = false) String token,
                                    @RequestParam(value = "email") String email,
                                    HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                user.setEmail(email);
                DashupService.getInstance().updateEmail(user);
            } catch (IllegalArgumentException illegalArgumentException) {
                return "redirect:/settings/#invalidEmail";
            } catch (EmailAlreadyInUseException emailAlreadyInUseException) {
                return "redirect:/settings/#emailAlreadyInUse";
            } catch (Exception exception) {
                return "redirect:/settings/#generalError";
            }
            return "redirect:/settings/#changedEmail";
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/changeUserName")
    public String handleChangeUserName(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(value = "userName") String userName,
                                       HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                user.setUserName(userName);
                DashupService.getInstance().updateUserName(user);
            } catch (EmailAlreadyInUseException emailAlreadyInUseException) {
                return "redirect:/settings/#userNameAlreadyInUse";
            } catch (Exception exception) {
                return "redirect:/settings/#generalError";
            }
            return "redirect:/settings/#changedUserName";
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/changeLanguage")
    public String handleChangeLanguage(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(value = "lang") String lang,
                                       HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Settings settings = new Settings();
            settings.setLanguage(Locale.forLanguageTag(lang));
            user.setSettings(settings);
            DashupService.getInstance().updateLanguage(user);
        }
        return "redirect:/settings/#changedLanguage";
    }

    @PostMapping(value = "/changePassword")
    public String handleChangePassword(@CookieValue(name = "token", required = false) String token,
                                       @RequestParam(name = "oldPassword") String oldPassword,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                DashupService.getInstance().updatePassword(user, oldPassword, newPassword);
            } catch (IllegalArgumentException ignored) {
                return "redirect:/settings/#oldPasswordWrong";
            }
            return "redirect:/settings/#changedPassword";
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/changeLayout")
    public String handleLayout(@CookieValue(name = "token", required = false) String token,
                               @RequestParam("theme") String theme,
                               @RequestParam("backgroundImage") String backgroundImage,
                               HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            user.getSettings().setTheme(Settings.Theme.getThemeByTechnicalName(theme));
            user.getSettings().setBackgroundImage(backgroundImage);
            DashupService.getInstance().updateSettings(user, false);
            return "redirect:/settings/#changedLayout";
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/changePersonalInfo")
    public String handlePersonalInfo(@CookieValue(name = "token", required = false) String token,
                                     @RequestParam("name") String name,
                                     @RequestParam("surname") String surname,
                                     @RequestParam("birthDate") String birthDate,
                                     @RequestParam("company") String company,
                                     @RequestParam("bio") String bio,
                                     HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            user.setName(name);
            user.setSurname(surname);
            if (birthDate != null && !birthDate.isBlank()) {
                user.setBirthDate(LocalDate.parse(birthDate));
            }
            user.setCompany(company);
            user.setBio(bio);
            DashupService.getInstance().updatePersonalInformation(user);
            return "redirect:/settings/#changedPersonalInfo";
        }
        return "redirect:/login";
    }
}