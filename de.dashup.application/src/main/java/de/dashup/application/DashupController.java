package de.dashup.application;

import de.dashup.application.local.LocalStorage;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class DashupController {

    @RequestMapping("/")
    public String main(Model model, HttpServletRequest request) {
        User user = (User) LocalStorage.readObjectFromSession(request, "user");
        if (user != null) {
            model.addAttribute("name", user.getFullName());
            return "index";
        } else {
            return "redirect:/welcome";
        }
    }

    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        User user = (User) LocalStorage.readObjectFromSession(request, "user");
        if (user != null) {
            return "redirect:/";
        } else {
            return "welcome";
        }
    }

    @RequestMapping("/entry")
    public String delegateEntry() {
        System.out.println("Delegating to the entry controller");
        return "redirect:/entry/login";
    }
}
