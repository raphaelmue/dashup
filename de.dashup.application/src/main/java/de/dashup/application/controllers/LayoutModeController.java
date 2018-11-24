package de.dashup.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/layoutmode")
public class LayoutModeController {

    @RequestMapping(value = "/layoutMode")
    public String login() {



        return "layoutMode";
    }

}


