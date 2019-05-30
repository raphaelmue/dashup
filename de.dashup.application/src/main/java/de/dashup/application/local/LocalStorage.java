package de.dashup.application.local;

import de.dashup.model.service.DashupService;
import de.dashup.shared.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class LocalStorage {
    private static LocalStorage instance;

    private LocalStorage() {
    }

    public static LocalStorage getInstance() {
        if (instance == null) {
            instance = new LocalStorage();
        }
        return instance;
    }

    public Object readObjectFromSession(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public void writeObjectToSession(HttpServletRequest request, String key, Serializable serializable) {
        request.getSession().setAttribute(key, serializable);
    }

    public User getUser(HttpServletRequest request, String token) throws SQLException {
        User user = (User) LocalStorage.getInstance().readObjectFromSession(request, "user");
        if ((user != null || token != null && !token.isEmpty()) && (token != null && !token.isEmpty())) {
            user = DashupService.getInstance().getUserByToken(token);
            if (user != null) {
                user.setSettings(DashupService.getInstance().getSettingsOfUser(user));
            }
        }
        return user;
    }

    public void deleteSessionAttribute(HttpServletRequest request, String key) {
        request.removeAttribute(key);
    }

    public Optional<String> readCookie(HttpServletRequest request, String key) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(key))
                .map(Cookie::getValue)
                .findAny();
    }

    public void writeCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
