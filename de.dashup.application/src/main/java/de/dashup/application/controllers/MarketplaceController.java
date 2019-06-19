package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import de.dashup.shared.layout.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/marketplace")
public class MarketplaceController {
    /**
     * Literals
     */
    private static final String PUBLISH_ATTRIBUTE_NAME = "publisher";
    private static final String REDIRECT_DETAIL_VIEW = "redirect:/marketplace/detailView/";

    @GetMapping(value = "/")
    public String marketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplace", user -> {
            model.addAttribute("carouselWidget", DashupService.getInstance().getPanelById(1));
            int[] featuredWidgets = {1, 2, 3, 4};
            model.addAttribute("featuredWidgets", DashupService.getInstance().getFeaturedWidgets(featuredWidgets));
            model.addAttribute("bestRated", DashupService.getInstance().getTopWidgets("avg_of_ratings"));
            model.addAttribute("mostDownloaded", DashupService.getInstance().getTopWidgets("number_of_downloads"));
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            model.addAttribute(PUBLISH_ATTRIBUTE_NAME, DashupService.getInstance().getAllPublisher());
        });
    }

    @GetMapping("/search")
    public String searchMarketplace(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request,
                                    @RequestParam("searchQuery") String searchQuery, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "rating", required = false) String rating, @RequestParam(value = "categories", required = false) List<String> categories, @RequestParam(value = "tags", required = false) List<String> tags,@RequestParam(value = "publisher", required = false) List<String> publisher) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "marketplaceSearchResult", user -> {
            List<Widget> widgets;
            if (tags != null) {
                widgets = DashupService.getInstance().findWidgetByName(searchQuery, date, rating, categories, tags,publisher);
            } else {
                widgets = DashupService.getInstance().findWidgetByName(searchQuery, date, rating, categories,publisher);
            }
            model.addAttribute("widgets", widgets);
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            model.addAttribute(PUBLISH_ATTRIBUTE_NAME, DashupService.getInstance().getAllPublisher());
        });
    }

    @GetMapping(value = "/detailView/{widgetId}")
    public String detailView(@CookieValue(name = "token", required = false) String token, @PathVariable(value = "widgetId") int widgetID, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "panelDetailView", user -> {
            Widget widget = DashupService.getInstance().getPanelById(widgetID);
            model.addAttribute(widget);
            User publisher = DashupService.getInstance().getUserById(widget.getPublisherId());
            model.addAttribute(PUBLISH_ATTRIBUTE_NAME, publisher);
            model.addAttribute("tags", DashupService.getInstance().getTagsByPanelId(widgetID));
            model.addAttribute("ratings", DashupService.getInstance().getRatingsByWidgetID(widgetID));
            user = DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
            model.addAttribute("similarWidgets", DashupService.getInstance().getSimilarWidgets(widget.getCategory(), widgetID));
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
                return REDIRECT_DETAIL_VIEW + widgetId + "#addedRating";
            } else {
                return REDIRECT_DETAIL_VIEW + widgetId + "#faieldToAddRating";
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
                return REDIRECT_DETAIL_VIEW + widgetId + "#addedWidget";
            } else {
                return REDIRECT_DETAIL_VIEW + widgetId + "#failedToAddWidget";
            }
        }
        return "redirect:/login";
    }
}
