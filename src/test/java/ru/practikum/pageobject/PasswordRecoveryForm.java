package ru.practikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryForm {
    private final WebDriver driver;
    private By signInButtonInPasswordRecoveryForm = By.xpath("//main//a[normalize-space()='Войти']");

    public PasswordRecoveryForm(WebDriver driver) { this.driver = driver; }

    @Step("На странице восстановления пароля нажать 'Войти'")
    public void clickSignInButtonInPasswordRecoveryForm() {
        driver.findElement(signInButtonInPasswordRecoveryForm).click();
    }
}
