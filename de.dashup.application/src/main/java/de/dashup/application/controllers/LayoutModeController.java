package de.dashup.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/layoutmode")
public class LayoutModeController {

    @RequestMapping(value = "/layoutMode")
    public String login() {
        return "layoutMode";
    }

    @RequestMapping(value = "/confirmChanges", method = RequestMethod.POST)
    public String confirm(Model model, HttpServletRequest request) {

        System.out.println("Post rec");
        return "layoutMode";

    }



}


