package de.dashup.application.controllers;

import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping("/layout")
public class LayoutController {

    @RequestMapping("/")
    public String layout(@CookieValue(name = "token", required = false) String token,
                       HttpServletRequest request, Model model) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            return "layout";
        }
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/handleLayout", method = RequestMethod.POST)
    public String handleLogin(@CookieValue(name = "token", required = false) String token,
                              @RequestParam(name = "background_color") String background_color,
                              @RequestParam(name = "background_image") String background_image,
                              @RequestParam(name = "heading_size") int heading_size,
                              @RequestParam(name = "heading_color") String heading_color,
                              @RequestParam(name = "font") String font,
                              HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            boolean success = DashupService.getInstance().changeLayout(user, background_color, background_image, heading_size, heading_color, font);
            if(success){
                response.setStatus(200);
            } else {
                response.setStatus(500);
            }
            return "layout";
        }
        return "redirect:/entry/login";
    }

}