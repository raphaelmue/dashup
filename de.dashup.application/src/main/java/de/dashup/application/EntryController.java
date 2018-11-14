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
@RequestMapping("/entry")
public class EntryController {

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        System.out.println("Rendering login page");
        return "login";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String verifyUser(@RequestParam String email, @RequestParam String password, Model model){
        model.addAttribute("name", email);
        return "index";
    }

    @RequestMapping(value = "/handleLogin", method = RequestMethod.POST)
    public String handleLogin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password, HttpServletRequest request) throws SQLException {
        User user = DashupService.getInstance().checkCredentials(email, password);
        LocalStorage.writeObjectToSession(request, "user", user);
        return "redirect:/";
    }

}