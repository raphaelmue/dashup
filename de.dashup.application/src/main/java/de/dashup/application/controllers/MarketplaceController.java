package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import de.dashup.shared.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @RequestMapping("/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            model.addAttribute("bestRated", DashupService.getInstance().getBestRatedWidgets());
        });
    }

    @RequestMapping("/search")
    public String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }

    @RequestMapping("/detailView/{widgetId}")
    public String detailView(@CookieValue(name = "token", required = false) String token, @PathVariable(value = "widgetId") int widgetID, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Widget widget = DashupService.getInstance().getPanelById(widgetID);
            model.addAttribute(widget);
            model.addAttribute("tags", DashupService.getInstance().getTagsByPanelId(widgetID));
            model.addAttribute("ratings", DashupService.getInstance().getRatingsByWidgetID(widgetID));
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
        });
    }

    @RequestMapping("/detailView/{widgetId}/addRating")
    public String addRating(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request,
                            @PathVariable(value = "widgetId") int widgetId,
                            @RequestParam("title") String title,
                            @RequestParam("text") String text,
                            @RequestParam("rating") int rating) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            boolean success = DashupService.getInstance().addRating(user,title,text,rating,widgetId);
            if (success) {
                return "redirect:/marketplace/detailView/" + widgetId + "#addedRating";
            }else{
                return "redirect:/marketplace/detailView/" + widgetId + "#faieldToAddRating";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping("/detailView/{widgetId}/addWidget")
    public String addWidget(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request,
                            @PathVariable(value = "widgetId") int widgetId,
                            @RequestParam("sectionId") int sectionId) throws SQLException{

        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            boolean success = DashupService.getInstance().addWidget(user, widgetId,sectionId);
            if (success) {
                return "redirect:/marketplace/detailView/" + widgetId + "#addedWidget";
            }else{
                return "redirect:/marketplace/detailView/" + widgetId + "#faieldToAddWidget";
            }
        }
        return "redirect:/login";
    }
}
