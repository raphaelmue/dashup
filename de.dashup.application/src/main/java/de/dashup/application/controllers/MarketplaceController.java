package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.layout.Widget;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/marketplace")
public class MarketplaceController {

    @GetMapping(value = "/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            model.addAttribute("carouselWidget", DashupService.getInstance().getPanelById(1));
            int[] featuredWidgets = {1, 2, 3, 4};
            model.addAttribute("featuredWidgets", DashupService.getInstance().getFeaturedWidgets(featuredWidgets));
            model.addAttribute("bestRated", DashupService.getInstance().getTopWidgets("avg_of_ratings"));
            model.addAttribute("mostDownloaded", DashupService.getInstance().getTopWidgets("number_of_downloads"));
        });
    }

    @PostMapping(value = "/search")
    public String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            // tbd
        });
    }

    @GetMapping(value = "/detailView/{widgetId}")
    public String detailView(@CookieValue(name = "token", required = false) String token, @PathVariable(value = "widgetId") int widgetID, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Widget widget = DashupService.getInstance().getPanelById(widgetID);
            model.addAttribute(widget);
            User publisher = DashupService.getInstance().getUserById(widget.getPublisherId());
            model.addAttribute("publisher", publisher);
            model.addAttribute("tags", DashupService.getInstance().getTagsByPanelId(widgetID));
            model.addAttribute("ratings", DashupService.getInstance().getRatingsByWidgetID(widgetID));
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
        });
    }

    @PostMapping(value = "/detailView/{widgetId}/addRating")
    public String addRating(@CookieValue(name = "token", required = false) String token, HttpServletRequest request,
                            @PathVariable(value = "widgetId") int widgetId,
                            @RequestParam("title") String title,
                            @RequestParam("text") String text,
                            @RequestParam("rating") int rating) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            boolean success = DashupService.getInstance().addRating(user, title, text, rating, widgetId);
            if (success) {
                return "redirect:/marketplace/detailView/" + widgetId + "#addedRating";
            } else {
                return "redirect:/marketplace/detailView/" + widgetId + "#faieldToAddRating";
            }
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/detailView/{widgetId}/addWidgetToPersonalDashup")
    public String addWidget(@CookieValue(name = "token", required = false) String token, HttpServletRequest request,
                            @PathVariable(value = "widgetId") int widgetId,
                            @RequestParam("sectionId") int sectionId,
                            @RequestParam("widgetSize") String widgetSize) throws SQLException {

        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            boolean success = DashupService.getInstance().addWidgetToPersonalDashup(user, widgetId, sectionId, widgetSize);
            if (success) {
                return "redirect:/marketplace/detailView/" + widgetId + "#addedWidget";
            } else {
                return "redirect:/marketplace/detailView/" + widgetId + "#failedToAddWidget";
            }
        }
        return "redirect:/login";
    }
}
