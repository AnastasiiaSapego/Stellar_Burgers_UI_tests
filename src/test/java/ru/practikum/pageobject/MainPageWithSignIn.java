package ru.practikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class MainPageWithSignIn extends BasePage {

    private final By makeOrderButton = By.xpath("//main//button[normalize-space()='Оформить заказ']");

    public MainPageWithSignIn(WebDriver driver) { super(driver); }

    @Step("Ждём и возвращаем текст кнопки 'Оформить заказ'")
    public String waitAndGetMakeOrderButtonText() {
        try {
            return waitVisible(makeOrderButton).getText();
        } catch (TimeoutException e) {
            driver.get(MainPageWithoutSignIn.URL);
            return waitVisible(makeOrderButton).getText();
        }
    }
}


