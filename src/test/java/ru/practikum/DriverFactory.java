package ru.practikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() {}

    public static WebDriver create() {
        String browser = getProp("BROWSER", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(getProp("HEADLESS", "false"));

        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");

        switch (browser) {
            case "yandex": {
                String yaDriver = getProp("YA_DRIVER_PATH", null);
                if (yaDriver == null || yaDriver.isEmpty()) {
                    throw new IllegalStateException("Укажи путь к yandexdriver в -DYA_DRIVER_PATH=...");
                }
                System.setProperty("webdriver.chrome.driver", yaDriver);

                String yaBin = getProp("YA_BROWSER_BIN", null);
                if (yaBin != null && !yaBin.isEmpty()) {
                    options.setBinary(yaBin);
                }
                return new ChromeDriver(options);
            }
            default: {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
            }
        }
    }

    private static String getProp(String key, String def) {
        String sys = System.getProperty(key);
        if (sys != null) return sys;
        String env = System.getenv(key);
        if (env != null) return env;
        return def;
    }
}
