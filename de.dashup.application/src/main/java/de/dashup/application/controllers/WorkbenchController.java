package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.service.DashupService;
import de.dashup.shared.Draft;
import de.dashup.shared.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

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

            for (Draft draft : user.getDrafts()) {
                if (draft.getId() == draftId) {
                    model.addAttribute("currentDraft", draft);
                    model.addAttribute("code", draft);
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
            DashupService.getInstance().deleteDraft(draftId);
            return "redirect:/workbench/#deletedDraft";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/draft/{draftId}/changeInformation", method = RequestMethod.POST)
    public String handleChangeDraftInformation(@CookieValue(name = "token", required = false) String token,
                                               HttpServletRequest request,
                                               @PathVariable(value = "draftId") int draftId,
                                               @RequestParam(value = "draftName") String name,
                                               @RequestParam(value = "shortDescription") String shortDescription,
                                               @RequestParam(value = "description") String description) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = new Draft();
            draft.setId(draftId);
            draft.setName(name);
            draft.setShortDescription(shortDescription);
            draft.setDescription(description);
            DashupService.getInstance().updateDraftInformation(draft);
            return "redirect:/workbench/draft/" + draftId + "#changedInformation";
        }
        return "redirect:/login";
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
            Draft draft = new Draft();
            draft.setId(draftId);
            if (codeSmall != null) {
                draft.setCodeSmall(URLDecoder.decode(codeSmall, StandardCharsets.UTF_8.name()));
            }
            if (codeMedium != null) {
                draft.setCodeMedium(URLDecoder.decode(codeMedium, StandardCharsets.UTF_8.name()));
            }
            if (codeLarge != null) {
                draft.setCodeLarge(URLDecoder.decode(codeLarge, StandardCharsets.UTF_8.name()));
            }
            DashupService.getInstance().updateDraftInformation(draft);
            return "redirect:/workbench/draft/" + draftId + "#changedCode";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/draft/{draftId}/publishDraft")
    public String handlePublishDraft(@CookieValue(name = "token", required = false) String token,
                                        HttpServletRequest request,
                                        @PathVariable(value = "draftId") int draftId) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            try {
                DashupService.getInstance().publishDraft(draftId);
            } catch (IllegalArgumentException e) {
                return "redirect:/workbench/draft/" + draftId + "#draftNotValid";
            }
            return "redirect:/workbench/#publishedDraft";
        }
        return "redirect:/login";
    }
}
