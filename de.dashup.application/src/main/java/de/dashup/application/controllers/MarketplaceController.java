package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Panel;
import de.dashup.shared.Rating;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @RequestMapping("/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            model.addAttribute("bestRated",DashupService.getInstance().getBestRatedPanels());
        });
    }
    @RequestMapping("/search")
    public  String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }
    @RequestMapping("/detailView/{panelId}")
    public  String detailView(@CookieValue(name = "token", required = false) String token, @PathVariable(value = "panelId") int panelID, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Panel panel = DashupService.getInstance().getPanelById(panelID);
            model.addAttribute(panel);
            model.addAttribute("tags",DashupService.getInstance().getTagsByPanelId(panelID));
            model.addAttribute("ratings", DashupService.getInstance().getRatingsByPanelID(panelID));
        });
    }
}
