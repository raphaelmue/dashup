package de.dashup.test.utils;

import de.dashup.test.steps.GeneralStepdefs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DriverUtil {

    private static final String CHROME_DRIVER_WINDOWS = "./src/test/resources/de/dashup/test/chromedriver.exe";
    private static final String CHROME_DRIVER_LINUX = "/usr/bin/chromedriver";
    private static final String PATH_TO_CHROME_BINARY = "/etc/alternatives/google-chrome";

    private static final String GECKO_DRIVER_WINDOWS = "./src/test/resources/de/dashup/test/chromedriver.exe";
    private static final String GECKO_DRIVER_LINUX = "/usr/bin/geckodriver";
    private static final String PATH_TO_FIREFOX_BINARY = "/usr/bin/firefox";

    private static String getDriverPath(String profile) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (profile.equals("chrome")) {
                return CHROME_DRIVER_WINDOWS;
            } else if (profile.equals("firefox")) {
                return GECKO_DRIVER_WINDOWS;
            }
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            if ("chrome".equals(profile)) {
                return CHROME_DRIVER_LINUX;
            } else if ("firefox".equals(profile)) {
                return GECKO_DRIVER_LINUX;
            }
        } else {
            return "";
        }
        return "";
    }

     private static String getPathToChromeBinary() {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return PATH_TO_CHROME_BINARY;
        } else {
            return null;
        }
    }

    private  static String getPathToFirefoxBinary() {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return PATH_TO_FIREFOX_BINARY;
        } else {
            return null;
        }
    }

    private static WebDriver setUpChromeDriver() {
        final DesiredCapabilities desiredChromeCapabilities = DesiredCapabilities.chrome();
        final ChromeOptions chromeOptions = new ChromeOptions();

        if (DriverUtil.getPathToChromeBinary() != null) {
            chromeOptions.setBinary(DriverUtil.getPathToChromeBinary());
        }

        desiredChromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        System.setProperty("webdriver.chrome.driver", DriverUtil.getDriverPath("chrome"));
        WebDriver returningDriver = new ChromeDriver(desiredChromeCapabilities);
        returningDriver.manage().window().maximize();
        return returningDriver;
    }

    private static WebDriver setUpFirefoxDriver() {
        final DesiredCapabilities desiredFirefoxCapabilities = DesiredCapabilities.firefox();
        final FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (DriverUtil.getPathToChromeBinary() != null) {
            firefoxOptions.setBinary(DriverUtil.getPathToFirefoxBinary());
        }

        desiredFirefoxCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        System.setProperty("webdriver.gecko.driver", DriverUtil.getDriverPath("firefox"));
        WebDriver returningDriver = new FirefoxDriver(desiredFirefoxCapabilities);
        returningDriver.manage().window().maximize();
        return returningDriver;
    }

    public static WebDriver setUpDriver() throws IOException {
        InputStream is = DriverUtil.class.getResourceAsStream("../../../../my.properties");
        Properties p = new Properties();
        p.load(is);
        String name = p.getProperty("test.browser");
        WebDriver driver;
        if (name != null) {
            switch (name) {
                case "chrome":
                    driver = DriverUtil.setUpChromeDriver();
                    break;
                case "firefox":
                    driver = DriverUtil.setUpFirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("The argument " + name + " was read from properties, but is not expected!");
            }
        } else {
            driver = DriverUtil.setUpChromeDriver();
        }
        GeneralStepdefs.setDriver(driver);
        return driver;
    }
}
