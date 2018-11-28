package de.dashup.application.local;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class LocalStorage {
    private static LocalStorage INSTANCE;

    private LocalStorage() { }

    public static LocalStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalStorage();
        }
        return INSTANCE;
    }

    public Object readObjectFromSession(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public void writeObjectToSession(HttpServletRequest request, String key, Object object) {
        request.getSession().setAttribute(key, object);
    }

    public void deleteSessionAttribute(HttpServletRequest request, String key) {
        request.removeAttribute(key);
    }

    public Optional<String> readCookie(HttpServletRequest request, String key) {
        return Arrays.stream(request.getCookies())
                .filter(key::equals)
                .map(Cookie::getValue)
                .findAny();
    }

    public void writeCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
