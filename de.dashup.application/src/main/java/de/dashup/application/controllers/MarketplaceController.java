package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @RequestMapping("/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            model.addAttribute("bestRated",DashupService.getInstance().getBestRatedWidgets());
            System.out.println("kjasd");
        });
    }
    @RequestMapping("/search")
    public  String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }
    @RequestMapping("/detailView/{widgetId}")
    public  String detailView(@CookieValue(name = "token", required = false) String token, @PathVariable(value = "widgetId") int widgetID, Model model, HttpServletRequest request) throws SQLException{
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Widget widget = DashupService.getInstance().getPanelById(widgetID);
            model.addAttribute(widget);
            model.addAttribute("tags",DashupService.getInstance().getTagsByPanelId(widgetID));
            model.addAttribute("ratings", DashupService.getInstance().getRatingsByWidgetID(widgetID));
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
        });
    }
}
