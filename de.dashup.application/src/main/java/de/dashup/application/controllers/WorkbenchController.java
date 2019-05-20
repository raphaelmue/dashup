package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.exceptions.InvalidCodeException;
import de.dashup.model.exceptions.MissingInformationException;
import de.dashup.model.service.DashupService;
import de.dashup.shared.*;
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
import java.util.List;

@Controller
@RequestMapping("/workbench")
public class WorkbenchController {

    @RequestMapping("/")
    public String workbench(@CookieValue(name = "token", required = false) String token,
                            HttpServletRequest request, Model model) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "workbench", user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute("drafts", user.getDrafts());
            model.addAttribute("publishedWidgets", DashupService.getInstance().getUsersWidgets(user));
        });
    }

    @RequestMapping("/createDraft")
    public String createDraft(@CookieValue(name = "token", required = false) String token,
                              HttpServletRequest request,
                              @RequestParam(value = "draftName") String draftName) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = DashupService.getInstance().createDraft(user, draftName);
            return "redirect:/workbench/draft/" + draft.getId();
        }
        return "redirect:/login";
    }

    @RequestMapping("/draft/{draftId}")
    public String workbenchDraft(@CookieValue(name = "token", required = false) String token,
                                 HttpServletRequest request, Model model,
                                 @PathVariable(value = "draftId") int draftId) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "workbench", user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute("drafts", user.getDrafts());
            model.addAttribute("publishedWidgets", DashupService.getInstance().getUsersWidgets(user));
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());

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

    @RequestMapping("/published/{publishedWidgetId}")
    public String workbenchPublished(@CookieValue(name = "token", required = false) String token,
                                     HttpServletRequest request, Model model,
                                     @PathVariable(value = "publishedWidgetId") int publishedWidgetId) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "workbench", user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute("drafts", user.getDrafts());
            List<Widget> publishedWidgets = DashupService.getInstance().getUsersWidgets(user);
            model.addAttribute("publishedWidgets", publishedWidgets);
            model.addAttribute("categories", Widget.Category.values());
            model.addAttribute("tags", DashupService.getInstance().getAllTags());
            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("sections", user.getSections());


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

    @RequestMapping(value = "/draft/{draftId}/deleteDraft")
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
                return "redirect:/workbench/";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/published/{publishedId}/deleteDraft")
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
                return "redirect:/workbench/";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/draft/{draftId}/changeInformation", method = RequestMethod.POST)
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
                } catch (InvalidCodeException | MissingInformationException ignored) {
                }
                return "redirect:/workbench/draft/" + draftId + "#changedInformation";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/published/{publishedId}/changeInformation", method = RequestMethod.POST)
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
                } catch (InvalidCodeException | MissingInformationException ignored) {
                }
                return "redirect:/workbench/published/" + publishedId + "#changedInformation";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
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

    @RequestMapping(value = "/draft/{draftId}/changeCode", method = RequestMethod.POST)
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
                    return "redirect:/workbench/draft/" + draftId + "#draftCodeNotValid";
                } catch (MissingInformationException ignored) {
                }
                return "redirect:/workbench/draft/" + draftId + "#changedCode";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/published/{publishedId}/changeCode", method = RequestMethod.POST)
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
                    return "redirect:/workbench/published/" + publishedId + "#draftCodeNotValid";
                } catch (MissingInformationException ignored) {
                }
                return "redirect:/workbench/published/" + publishedId + "#changedCode";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
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

    @RequestMapping(value = "/draft/{draftId}/addDraft")
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
                return "redirect:/workbench/draft/" + draftId + "#addedWidget";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/published/{publishedId}/addDraft")
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
                return "redirect:/workbench/published/" + publishedId + "#addedWidget";
            }
            return "redirect:/workbench/";
        }
        return "redirect:/login";
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

    @RequestMapping(value = "/draft/{draftId}/publishDraft", method = RequestMethod.POST)
    public String handlePublishDraft(@CookieValue(name = "token", required = false) String token,
                                     HttpServletRequest request,
                                     @PathVariable(value = "draftId") int draftId) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                DashupService.getInstance().publishDraft(draftId);
            } catch (MissingInformationException e) {
                return "redirect:/workbench/draft/" + draftId + "#draftNotValid";
            } catch (InvalidCodeException e) {
                return "redirect:/workbench/draft/" + draftId + "#draftCodeNotValid";
            }
            return "redirect:/workbench/#publishedDraft";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/draft/{draftId}/updateProperties", method = RequestMethod.POST)
    public String handleUpdateProperties(@CookieValue(name = "token", required = false) String token,
                                         HttpServletRequest request,
                                         @PathVariable(value = "draftId") int draftId,
                                         @RequestParam(value = "properties") String jsonProperties) throws SQLException, UnsupportedEncodingException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = new Draft();
            draft.setId(draftId);
            JSONArray jsonArray = new JSONArray(URLDecoder.decode(jsonProperties, StandardCharsets.UTF_8.name()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                draft.getProperties().put(jsonObject.getString("property"),
                        new Property(jsonObject.getInt("id"), jsonObject.getString("property"),
                                null, jsonObject.getString("type"), jsonObject.getString("defaultValue"), null));
            }
            DashupService.getInstance().updateWidgetProperties(draft);
            return "redirect:/workbench/draft/" + draftId + "#changedProperties";
        }
        return "redirect:/login";

    }
}
