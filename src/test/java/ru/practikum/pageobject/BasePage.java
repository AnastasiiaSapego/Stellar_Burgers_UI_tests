package ru.practikum.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final long TIMEOUT =
            Long.parseLong(System.getProperty("WAIT_TIMEOUT", "12"));

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement visible(By locator) {
        return waitVisible(locator);
    }

    protected WebElement present(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void type(By locator, String text) {
        RuntimeException last = null;
        for (int i = 0; i < 3; i++) {
            try {
                WebElement el = waitVisible(locator);
                el.click();
                el.clear();
                el.sendKeys(text);
                return;
            } catch (StaleElementReferenceException e) {
                last = e;
                sleep(200);
            }
        }
        if (last != null) throw last;
    }

    protected void jsClick(By locator) {
        WebElement el = waitVisible(locator);
        jsScrollIntoView(el);
        try {
            clickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void jsScrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
