package de.dashup.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashupController {

    @RequestMapping("/dashup")
    public ModelAndView main() {
        System.out.println("dashup controller.");
        return new ModelAndView("index");
    }
}
