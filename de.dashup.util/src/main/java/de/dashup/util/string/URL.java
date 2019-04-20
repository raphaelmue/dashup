package de.dashup.util.string;

import java.net.MalformedURLException;

public class URL {

    public static boolean isValidURL(String urlStr) {
        try {
            new java.net.URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}