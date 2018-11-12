package de.dashup.application;

import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class DashupController {

    @RequestMapping("/hello")
    public String main(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        System.out.println("rendering hello world page");
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping("/entry")
    public String delegateEntry() {
        System.out.println("Delegating to the entry controller");
        return "redirect:/entry/login";
    }

    @RequestMapping("/login")
    public String handleLogin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password, Model model) throws SQLException {
        User user = DashupService.getInstance().checkCredentials(email, password);
        model.addAttribute("name", user.getFullName());
        return "index";
    }

}
