package de.dashup.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class mainController {
    @RequestMapping("/main")
    public String main(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        System.out.println("mainDashboard accessed");
        model.addAttribute("name", name);
        return "mainDashboard";
    }
}
