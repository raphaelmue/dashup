package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Panel;
import de.dashup.shared.Section;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

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
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
        });
    }
}
