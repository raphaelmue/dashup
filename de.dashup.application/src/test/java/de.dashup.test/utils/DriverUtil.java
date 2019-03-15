package de.dashup.test.utils;

public class DriverUtil {

    private static final String CHROME_DRIVER_WINDOWS = "./src/test/resources/de/dashup/test/chromedriver.exe";
    private static final String CHROME_DRIVER_LINUX = "/usr/bin/chromedriver";
    private static final String PATH_TO_CHROME_BINARY = "/etc/alternatives/google-chrome";

    public static String getDriverPath(){
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return CHROME_DRIVER_WINDOWS;
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return CHROME_DRIVER_LINUX;
        } else {
            return "";
        }
    }

    public static String getPathToChromeBinary(){
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return PATH_TO_CHROME_BINARY;
        }else{
            return null;
        }
    }
}
