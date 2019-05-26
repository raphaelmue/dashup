package de.dashup.util.string;

import java.net.MalformedURLException;
import java.net.URL;

public class URLValidator {

    public static boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}