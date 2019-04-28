package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }

    @RequestMapping("/detailView")
    public String detailView(@CookieValue(name = "token", required = false) String token, @RequestParam(name = "panel_id") String panelID, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Widget panel = DashupService.getInstance().getPanelById(1);
            ArrayList<String> tags = DashupService.getInstance().getTagsByPanelId(1);
            model.addAttribute(panel);
            model.addAttribute(tags);
        });
    }
}
