package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @RequestMapping("/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            // tbd
        });
    }
    @RequestMapping("/search")
    public  String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }
    @RequestMapping("/detailView")
    public  String detailView(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            // tbd
        });
    }
}
