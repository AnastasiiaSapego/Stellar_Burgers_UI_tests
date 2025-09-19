package ru.practikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInForm extends BasePage {

    private final By emailInputByLabel =
            By.xpath("//form//fieldset[.//label[normalize-space()='Email']]//input[contains(@class,'input__textfield')]");
    private final By passwordInputByLabel =
            By.xpath("//form//fieldset[.//label[normalize-space()='Пароль']]//input[contains(@class,'input__textfield')]");
    private final By emailInputFallback =
            By.xpath("//form//fieldset[1]//input[@type='text' or @type='email' or contains(@class,'input__textfield')]");
    private final By passwordInputFallback =
            By.xpath("//form//fieldset[2]//input[@type='password' or contains(@class,'input__textfield')]");

    private final By signInButton  = By.xpath("//form//button[normalize-space()='Войти']");
    private final By registrationLink = By.xpath("//p/a[normalize-space()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[normalize-space()='Восстановить пароль']");

    public SignInForm(WebDriver driver) { super(driver); }

    @Step("Ожидаем загрузку формы входа")
    public void waitOpened() {
        if (driver.findElements(emailInputByLabel).isEmpty()) {
            visible(emailInputFallback);
        } else {
            visible(emailInputByLabel);
        }
    }

    @Step("Заполнить email")
    public void setEmailField(String email) {
        By locator = driver.findElements(emailInputByLabel).isEmpty()
                ? emailInputFallback
                : emailInputByLabel;
        type(locator, email);
    }

    @Step("Заполнить пароль")
    public void setPasswordField(String password) {
        By locator = driver.findElements(passwordInputByLabel).isEmpty()
                ? passwordInputFallback
                : passwordInputByLabel;
        type(locator, password);
    }

    @Step("Нажать 'Войти'")
    public void clickSignInButtonInSignInForm() {
        clickable(signInButton).click();
    }

    @Step("Ссылка 'Зарегистрироваться'")
    public void clickRegistrationButtonInSignInForm() {
        clickable(registrationLink).click();
    }

    @Step("Ссылка 'Восстановить пароль'")
    public void clickForgotPassword() {
        clickable(forgotPasswordLink).click();
    }

    @Step("Заполнить форму входа")
    public void fillOutSignInForm(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
    }
}