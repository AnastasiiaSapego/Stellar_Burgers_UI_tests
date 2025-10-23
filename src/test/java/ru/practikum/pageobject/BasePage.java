package ru.practikum.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final long TIMEOUT =
            Long.parseLong(System.getProperty("WAIT_TIMEOUT", "12"));
    
    private static final By MODAL_OVERLAY =
            By.cssSelector("div.Modal_modal_overlay__x2ZCr");

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

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitOverlayGone() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(MODAL_OVERLAY));
        } catch (TimeoutException ignored) {
        }
    }

    protected void type(By locator, String text) {
        RuntimeException last = null;

        for (int i = 0; i < 3; i++) {
            try {
                waitOverlayGone();
                WebElement el = waitVisible(locator);
                jsScrollIntoView(el);
                try { el.click(); } catch (ElementClickInterceptedException ignored) {}
                el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                el.sendKeys(text);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                last = e;
                sleep(200);
            }
        }

        try {
            WebElement el = waitVisible(locator);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                    el, text
            );
            return;
        } catch (RuntimeException e) {
            last = e;
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

    protected static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
