package de.dashup.application;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class DashupController {

    @RequestMapping("/hello")
    public String main(Model model, HttpServletRequest request) {
        System.out.println("rendering hello world page");
        User user = (User) LocalStorage.readObjectFromSession(request, "user");
        model.addAttribute("name", user.getFullName());
        return "index";
    }

    @RequestMapping("/entry")
    public String delegateEntry() {
        System.out.println("Delegating to the entry controller");
        return "redirect:/entry/login";
    }

    @RequestMapping(value = "/handleLogin", method = RequestMethod.POST)
    public String handleLogin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password, HttpServletRequest request) throws SQLException {
        User user = DashupService.getInstance().checkCredentials(email, password);
        LocalStorage.writeObjectToSession(request, "user", user);
        return "redirect:/hello";
    }

}
