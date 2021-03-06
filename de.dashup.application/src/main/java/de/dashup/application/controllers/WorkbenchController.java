package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.exceptions.InvalidCodeException;
import de.dashup.model.exceptions.MissingInformationException;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Draft;
import de.dashup.shared.Property;
import de.dashup.shared.Tag;
import de.dashup.shared.User;
import de.dashup.shared.layout.Section;
import de.dashup.shared.layout.Widget;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Controller
@RequestMapping("/workbench")
public class WorkbenchController {

    /**
     * Literals
     */
    private static final String WORKBENCH = "workbench";
    private static final String DRAFTS = "drafts";
    private static final String PUBLISHED_WIDGETS = "publishedWidgets";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_WORKBENCH = "redirect:/workbench/";
    private static final String REDIRECT_DRAFT = "redirect:/workbench/draft/";
    private static final String REDIRECT_PUBLISHED = "redirect:/workbench/published/";
    private static final String DRAFT_CODE_NOT_VALID_MSG = "#draftCodeNotValid";

    @GetMapping(value = "/")
    public String workbench(@CookieValue(name = "token", required = false) String token,
                            HttpServletRequest request, Model model) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, WORKBENCH, user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute(DRAFTS, user.getDrafts());
            model.addAttribute(PUBLISHED_WIDGETS, DashupService.getInstance().getUsersWidgets(user));
        });
    }

    @PostMapping(value = "/createDraft")
    public String createDraft(@CookieValue(name = "token", required = false) String token,
                              HttpServletRequest request,
                              @RequestParam(value = "draftName") String draftName) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = DashupService.getInstance().createDraft(user, draftName);
            return REDIRECT_DRAFT + draft.getId();
        }
        return REDIRECT_LOGIN;
    }

    @GetMapping(value = "/draft/{draftId}")
    public String workbenchDraft(@CookieValue(name = "token", required = false) String token,
                                 HttpServletRequest request, Model model,
                                 @PathVariable(value = "draftId") int draftId) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, WORKBENCH, user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute(DRAFTS, user.getDrafts());
            model.addAttribute(PUBLISHED_WIDGETS, DashupService.getInstance().getUsersWidgets(user));
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
            model.addAttribute("propertyTypes", Property.Type.values());

            for (Draft draft : user.getDrafts()) {
                if (draft.getId() == draftId) {
                    model.addAttribute("current", draft);
                    DashupService.getInstance().getTagsByWidget(draft);
                    model.addAttribute("currentTags", draft.getTags());
                    break;
                }
            }
        });
    }

    @GetMapping(value = "/published/{publishedWidgetId}")
    public String workbenchPublished(@CookieValue(name = "token", required = false) String token,
                                     HttpServletRequest request, Model model,
                                     @PathVariable(value = "publishedWidgetId") int publishedWidgetId) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, WORKBENCH, user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute(DRAFTS, user.getDrafts());
            List<Widget> publishedWidgets = DashupService.getInstance().getUsersWidgets(user);
            model.addAttribute(PUBLISHED_WIDGETS, publishedWidgets);
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());
            model.addAttribute("propertyTypes", Property.Type.values());

            for (Widget widget : publishedWidgets) {
                if (widget.getId() == publishedWidgetId) {
                    model.addAttribute("current", widget);
                    DashupService.getInstance().getTagsByWidget(widget);
                    model.addAttribute("currentTags", widget.getTags());
                    break;
                }
            }
        });
    }

    @PostMapping(value = "/draft/{draftId}/deleteDraft")
    public String handleDeleteDraft(@CookieValue(name = "token", required = false) String token,
                                    HttpServletRequest request,
                                    @PathVariable(value = "draftId") int draftId) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget draft = DashupService.getInstance().getWidgetOfUser(user, draftId);
            if (draft != null) {
                DashupService.getInstance().deleteDraft(draftId);
                return "redirect:/workbench/#deletedDraft";
            } else {
                return REDIRECT_WORKBENCH;
            }
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/published/{publishedId}/deleteDraft")
    public String handleDeleteWidget(@CookieValue(name = "token", required = false) String token,
                                     HttpServletRequest request,
                                     @PathVariable(value = "publishedId") int publishedId) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget widget = DashupService.getInstance().getWidgetOfUser(user, publishedId);
            if (widget != null) {
                DashupService.getInstance().deleteDraft(widget.getId());
                return "redirect:/workbench/#deletedDraft";
            } else {
                return REDIRECT_WORKBENCH;
            }
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/draft/{draftId}/changeInformation")
    public String handleChangeDraftInformation(@CookieValue(name = "token", required = false) String token,
                                               HttpServletRequest request,
                                               @PathVariable(value = "draftId") int draftId,
                                               @RequestParam(value = "draftName") String name,
                                               @RequestParam(value = "shortDescription") String shortDescription,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "category") String category,
                                               @RequestParam(value = "tags") String tagsJSON) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget draft = DashupService.getInstance().getWidgetOfUser(user, draftId);
            if (draft != null) {
                try {
                    updateInformation(draft, draftId, name, shortDescription, description, category, tagsJSON);
                } catch (InvalidCodeException | MissingInformationException e) {
                    ControllerHelper.getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
                return REDIRECT_DRAFT + draftId + "#changedInformation";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/published/{publishedId}/changeInformation")
    public String handleChangeWidgetInformation(@CookieValue(name = "token", required = false) String token,
                                                HttpServletRequest request,
                                                @PathVariable(value = "publishedId") int publishedId,
                                                @RequestParam(value = "draftName") String name,
                                                @RequestParam(value = "shortDescription") String shortDescription,
                                                @RequestParam(value = "description") String description,
                                                @RequestParam(value = "category") String category,
                                                @RequestParam(value = "tags") String tagsJSON) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget widget = DashupService.getInstance().getWidgetOfUser(user, publishedId);
            if (widget != null) {
                try {
                    updateInformation(widget, publishedId, name, shortDescription, description, category, tagsJSON);
                } catch (InvalidCodeException | MissingInformationException e) {
                    ControllerHelper.getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
                return REDIRECT_PUBLISHED + publishedId + "#changedInformation";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    private void updateInformation(Widget widget, int publishedId, String name, String shortDescription, String description, String category, String tagsJSON) throws UnsupportedEncodingException, SQLException, InvalidCodeException, MissingInformationException {
        widget.setId(publishedId);
        widget.setName(name);
        widget.setShortDescription(shortDescription);
        widget.setDescription(description);
        widget.setCategory(category);

        JSONArray json = new JSONArray(URLDecoder.decode(tagsJSON, StandardCharsets.UTF_8.name()));
        for (int i = 0; i < json.length(); i++) {
            widget.getTags().add(new Tag(json.getJSONObject(i).getInt("id"), json.getJSONObject(i).getString("name")));
        }

        DashupService.getInstance().updateWidgetInformation(widget);
    }

    @PostMapping(value = "/draft/{draftId}/changeCode")
    public String handleChangeDraftCode(@CookieValue(name = "token", required = false) String token,
                                        HttpServletRequest request,
                                        @PathVariable(value = "draftId") int draftId,
                                        @RequestParam(value = "code_small", required = false) String codeSmall,
                                        @RequestParam(value = "code_medium", required = false) String codeMedium,
                                        @RequestParam(value = "code_large", required = false) String codeLarge) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget draft = DashupService.getInstance().getWidgetOfUser(user, draftId);
            if (draft != null) {
                try {
                    updateCode(draftId, codeSmall, codeMedium, codeLarge, draft);
                } catch (InvalidCodeException e) {
                    return REDIRECT_DRAFT + draftId + DRAFT_CODE_NOT_VALID_MSG;
                } catch (MissingInformationException e) {
                    ControllerHelper.getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
                return REDIRECT_DRAFT + draftId + "#changedCode";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/published/{publishedId}/changeCode")
    public String handleChangeWidgetCode(@CookieValue(name = "token", required = false) String token,
                                         HttpServletRequest request,
                                         @PathVariable(value = "publishedId") int publishedId,
                                         @RequestParam(value = "code_small", required = false) String codeSmall,
                                         @RequestParam(value = "code_medium", required = false) String codeMedium,
                                         @RequestParam(value = "code_large", required = false) String codeLarge) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget widget = DashupService.getInstance().getWidgetOfUser(user, publishedId);
            if (widget != null) {
                try {
                    updateCode(publishedId, codeSmall, codeMedium, codeLarge, widget);
                } catch (InvalidCodeException e) {
                    return REDIRECT_PUBLISHED + publishedId + DRAFT_CODE_NOT_VALID_MSG;
                } catch (MissingInformationException e) {
                    ControllerHelper.getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
                return REDIRECT_PUBLISHED + publishedId + "#changedCode";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    private void updateCode(int id, String codeSmall, String codeMedium, String codeLarge, Widget widget)
            throws UnsupportedEncodingException, SQLException, InvalidCodeException, MissingInformationException {
        widget.setId(id);
        if (codeSmall != null) {
            widget.setCodeSmall(URLDecoder.decode(codeSmall, StandardCharsets.UTF_8.name()));
        }
        if (codeMedium != null) {
            widget.setCodeMedium(URLDecoder.decode(codeMedium, StandardCharsets.UTF_8.name()));
        }
        if (codeLarge != null) {
            widget.setCodeLarge(URLDecoder.decode(codeLarge, StandardCharsets.UTF_8.name()));
        }
        DashupService.getInstance().updateWidgetInformation(widget, !widget.getIsVisible());
    }

    @PostMapping(value = "/draft/{draftId}/addDraft")
    public String handleAddDraft(@CookieValue(name = "token", required = false) String token,
                                 HttpServletRequest request,
                                 @PathVariable(value = "draftId") int draftId,
                                 @RequestParam(value = "sectionId") int sectionId,
                                 @RequestParam(value = "size") String size) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget draft = DashupService.getInstance().getWidgetOfUser(user, draftId);
            if (draft != null) {
                addWidgetToDashup(user, draftId, sectionId, size);
                return REDIRECT_DRAFT + draftId + "#addedWidget";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/published/{publishedId}/addDraft")
    public String handleAddWidget(@CookieValue(name = "token", required = false) String token,
                                  HttpServletRequest request,
                                  @PathVariable(value = "publishedId") int publishedId,
                                  @RequestParam(value = "sectionId") int sectionId,
                                  @RequestParam(value = "size") String size) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Widget widget = DashupService.getInstance().getWidgetOfUser(user, publishedId);
            if (widget != null) {
                addWidgetToDashup(user, publishedId, sectionId, size);
                return REDIRECT_PUBLISHED + publishedId + "#addedWidget";
            }
            return REDIRECT_WORKBENCH;
        }
        return REDIRECT_LOGIN;
    }

    private void addWidgetToDashup(User user, int publishedId, int sectionId, String size) throws SQLException {
        Section section;
        if (sectionId == -1) {
            DashupService.getInstance().getSectionsAndPanels(user);
            section = new Section(-1, null, user.getSections().size());
            section.setId(DashupService.getInstance().addNewSection(user, section));
        } else {
            section = new Section();
            section.setId(sectionId);
        }
        Widget draft = new Draft();
        draft.setId(publishedId);
        DashupService.getInstance().addWidgetToSection(draft, section, size);
    }

    @PostMapping(value = "/draft/{draftId}/publishDraft")
    public String handlePublishDraft(@CookieValue(name = "token", required = false) String token,
                                     HttpServletRequest request,
                                     @PathVariable(value = "draftId") int draftId) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                DashupService.getInstance().publishDraft(draftId);
            } catch (MissingInformationException e) {
                return REDIRECT_DRAFT + draftId + "#draftNotValid";
            } catch (InvalidCodeException e) {
                return REDIRECT_DRAFT + draftId + DRAFT_CODE_NOT_VALID_MSG;
            }
            return "redirect:/workbench/#publishedDraft";
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping(value = "/draft/{draftId}/updateProperties")
    public String handleUpdateProperties(@CookieValue(name = "token", required = false) String token,
                                         HttpServletRequest request,
                                         @PathVariable(value = "draftId") int draftId,
                                         @RequestParam(value = "properties") String jsonProperties,
                                         @RequestParam(value = "propertiesToDelete") String jsonPropertiesToDelete) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = new Draft();
            draft.setId(draftId);
            JSONArray jsonArray = new JSONArray(URLDecoder.decode(jsonProperties, StandardCharsets.UTF_8.name()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                draft.getProperties().put(jsonObject.getString("property"),
                        new Property(jsonObject.getInt("id"),
                                jsonObject.getString("property"),
                                jsonObject.getString("name"),
                                jsonObject.getString("type"),
                                jsonObject.getString("defaultValue"),
                                null));
            }

            List<Integer> propertiesToDelete = new ArrayList<>();
            jsonArray = new JSONArray(URLDecoder.decode(jsonPropertiesToDelete, StandardCharsets.UTF_8.name()));
            for (int i = 0; i < jsonArray.length(); i++) {
                propertiesToDelete.add(jsonArray.getInt(i));
            }

            DashupService.getInstance().updateWidgetProperties(draft, propertiesToDelete);
            return REDIRECT_DRAFT + draftId + "#changedProperties";
        }
        return REDIRECT_LOGIN;
    }
}
