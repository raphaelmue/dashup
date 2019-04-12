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
        });
    }

    @RequestMapping("/createDraft")
    public String createDraft(@CookieValue(name = "token", required = false) String token,
                              HttpServletRequest request, Model model,
                              @RequestParam(value = "draftName") String draftName) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Draft draft = DashupService.getInstance().createDraft(user, draftName);
            return "redirect:/workbench/" + draft.getId() + "/";
        }
        return "redirect:/login";
    }

    @RequestMapping("/{draftId}")
    public String workbenchDraft(@CookieValue(name = "token", required = false) String token,
                                 HttpServletRequest request, Model model,
                                 @PathVariable(value = "draftId") int draftId) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "workbench", user -> {
            DashupService.getInstance().getUsersDrafts(user);
            model.addAttribute("drafts", user.getDrafts());

            for (Draft draft : user.getDrafts()) {
                if (draft.getId() == draftId) {
                    model.addAttribute("currentDraft", draft);
                    model.addAttribute("code", draft);
                }
            }
        });
    }

    @RequestMapping(value = "/{draftId}/changeInformation", method = RequestMethod.POST)
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
            return "redirect:/workbench/" + draftId + "/#changedInformation";
        }
        return "redirect:/login";
    }
}
