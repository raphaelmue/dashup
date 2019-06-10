package de.dashup.application.controllers;

import de.dashup.application.controllers.util.ControllerHelper;
import de.dashup.application.local.LocalStorage;
import de.dashup.model.builder.DashupBuilder;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import de.dashup.shared.widgets.FinanceChart;
import de.dashup.shared.widgets.FinanceList;
import de.dashup.shared.widgets.Todo;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class DashupController {
    private final LocalStorage localStorage = LocalStorage.getInstance();

    @GetMapping(value = "/")
    public String main(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) throws SQLException {
        return ControllerHelper.defaultMapping(token, request, model, "index", user -> {
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("backgroundImage", user.getSettings().getBackgroundImage());

            Map<String, String> layout = DashupService.getInstance().loadLayout(user);
            for (Map.Entry<String, String> entry : layout.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }

            DashupService.getInstance().getSectionsAndPanels(user);
            model.addAttribute("content", DashupBuilder.buildUsersPanels(user));
        });
    }

    @PostMapping(value = "/handleLogout")
    public String handleLogout(@CookieValue(name = "token", required = false) String token,
                               HttpServletRequest request, HttpServletResponse response) {
        this.localStorage.writeObjectToSession(request, "user", null);
        if (token != null && !token.isEmpty()) {
            try {
                DashupService.getInstance().deleteToken(token);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.localStorage.deleteCookie(response, "token");

        return "redirect:/login";
    }

    @PostMapping(value = "/todo", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleTodo(@CookieValue(name = "token", required = false) String token,
                                             @RequestBody Todo data,
                                             Locale locale,
                                             HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = LocalStorage.getInstance().getUser(request, token);
        JSONObject entity = new JSONObject();
        if (user != null) {
            DashupService.getInstance().saveTodoWidgetState(user, data);
            entity.put("message", "Success");
            return new ResponseEntity<>(entity.toString(), HttpStatus.OK);
        }
        entity.put("message", "Failure");
        return new ResponseEntity<>(entity.toString(), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/loadTodo")
    public @ResponseBody
    Todo handleLoadTodo(@CookieValue(name = "token", required = false) String token,
                        HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            Todo todo = DashupService.getInstance().loadTodoWidgetState(user);
            if (!todo.getList().isEmpty()) {
                return todo;
            }
        }
        return null;
    }

    @PostMapping(value = "/financeChart", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleFinanceChart(@CookieValue(name = "token", required = false) String token,
                                                     @RequestBody FinanceChart data,
                                                     Locale locale,
                                                     HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = LocalStorage.getInstance().getUser(request, token);
        JSONObject entity = new JSONObject();
        if (user != null) {
            DashupService.getInstance().saveFinanceChartWidgetState(user, data);
            entity.put("message", "Success");
            return new ResponseEntity<>(entity.toString(), HttpStatus.OK);
        }
        entity.put("message", "Failure");
        return new ResponseEntity<>(entity.toString(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/financeList", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handleFinanceList(@CookieValue(name = "token", required = false) String token,
                                                    @RequestBody FinanceList data,
                                                    Locale locale,
                                                    HttpServletRequest request) throws SQLException {
        ControllerHelper.setLocale(request, locale);
        User user = LocalStorage.getInstance().getUser(request, token);
        JSONObject entity = new JSONObject();
        if (user != null) {
            DashupService.getInstance().saveFinanceListWidgetState(user, data);
            entity.put("message", "Success");
            return new ResponseEntity<>(entity.toString(), HttpStatus.OK);
        }
        entity.put("message", "Failure");
        return new ResponseEntity<>(entity.toString(), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/loadFinanceChart")
    public @ResponseBody
    FinanceChart handleLoadFinanceChart(@CookieValue(name = "token", required = false) String token,
                                        HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            FinanceChart chart = DashupService.getInstance().loadFinanceChartWidgetState(user);
            if (!chart.getChart().isEmpty()) {
                return chart;
            }
        }
        return null;
    }

    @RequestMapping("/loadFinanceList")
    public @ResponseBody
    FinanceList handleLoadFinanceList(@CookieValue(name = "token", required = false) String token,
                                      HttpServletRequest request) throws SQLException {
        User user = LocalStorage.getInstance().getUser(request, token);
        if (user != null) {
            FinanceList list = DashupService.getInstance().loadFinanceListWidgetState(user);
            if (!list.getFinanceList().isEmpty()) {
                return list;
            }
        }
        return null;
    }

}