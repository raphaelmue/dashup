package de.dashup.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryController {

    @RequestMapping(value="/entry/login",method= RequestMethod.GET)
    public String login(){
        System.out.println("Rendering loin page");
        return "login";
    }

}