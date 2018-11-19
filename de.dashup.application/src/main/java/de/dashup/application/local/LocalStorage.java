package de.dashup.application.local;

import javax.servlet.http.HttpServletRequest;

public class LocalStorage {

    public static Object readObject(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static void writeObject(HttpServletRequest request, String key, Object object) {
        request.getSession().setAttribute(key, object);
    }
}
