package de.dashup.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntryController {

    @RequestMapping(value="/entry/login",method= RequestMethod.GET)
    public String login(){
        System.out.println("Rendering login page");
        return "login";
    }

    @RequestMapping(value="/entry/login",method= RequestMethod.POST)
    public String verifyUser(@RequestParam String email, @RequestParam String password, Model model){
        model.addAttribute("name", email);
        return "index";
    }

}