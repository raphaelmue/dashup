package de.dashup.test.utils;

public class DriverUtil {

    private static final String CHROME_DRIVER_WINDOWS = "./src/test/resources/de/dashup/test/chromedriver.exe";
    private static final String CHROME_DRIVER_LINUX = "/usr/bin/chromedriver";
    private static final String PATH_TO_CHROME_BINARY = "/etc/alternatives/google-chrome";

    private static final String GECKO_DRIVER_WINDOWS = "./src/test/resources/de/dashup/test/chromedriver.exe";
    private static final String GECKO_DRIVER_LINUX = "/usr/bin/geckodriver";
    private static final String PATH_TO_FIREFOX_BINARY = "/usr/bin/firefox";

    public static String getDriverPath(String profile) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (profile.equals("chrome")) {
                return CHROME_DRIVER_WINDOWS;
            } else if (profile.equals("firefox")) {
                return GECKO_DRIVER_WINDOWS;
            }
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            if (profile.equals("chrome")) {
                return CHROME_DRIVER_LINUX;
            } else if (profile.equals("firefox")) {
                return GECKO_DRIVER_LINUX;
            }
        } else {
            return "";
        }
        return "";
    }

    public static String getPathToChromeBinary() {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return PATH_TO_CHROME_BINARY;
        } else {
            return null;
        }
    }

    public static String getPathToFirefoxBinary() {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return PATH_TO_FIREFOX_BINARY;
        } else {
            return null;
        }
    }
}
