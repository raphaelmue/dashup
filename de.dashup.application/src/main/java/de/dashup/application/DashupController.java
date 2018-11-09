package de.dashup.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashupController {

    @RequestMapping("/hello")
    public String main(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        System.out.println("rednering hello world page");
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping("/entry")
    public String delegateEntry(){
        System.out.println("Delegating to the entry controller");
        return "redirect:/entry/login";
    }

}
