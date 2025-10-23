package ru.practikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationForm extends BasePage {

    private final By nameInput     = By.xpath("//form//fieldset[.//label[normalize-space()='Имя']]//input[contains(@class,'input__textfield')]");
    private final By emailInput    = By.xpath("//form//fieldset[.//label[normalize-space()='Email']]//input[contains(@class,'input__textfield')]");
    private final By passwordInput = By.xpath("//form//fieldset[.//label[normalize-space()='Пароль']]//input[contains(@class,'input__textfield')]");
    private final By registerButton= By.xpath("//form//button[normalize-space()='Зарегистрироваться']");
    private final By signInLink    = By.xpath("//p/a[normalize-space()='Войти']");
    private final By wrongPasswordError = By.xpath("//p[normalize-space()='Некорректный пароль']");

    public RegistrationForm(WebDriver driver) {
        super(driver);
    }

    @Step("Ожидаем загрузку формы регистрации")
    public void waitOpened() {
        waitOverlayGone();
        visible(nameInput);
    }

    @Step("Заполнить имя: {name}")
    public void setNameField(String name) {
        type(nameInput, name);
    }

    @Step("Заполнить email: {email}")
    public void setEmailField(String email) {
        type(emailInput, email);
    }

    @Step("Заполнить пароль (длина={password.length})")
    public void setPasswordField(String password) {
        type(passwordInput, password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegistrationButton() {
        clickable(registerButton).click();
        new SignInForm(driver).waitOpened();
    }

    @Step("Нажать ссылку 'Войти' в форме регистрации")
    public void clickSignInButtonInRegistrationForm() {
        clickable(signInLink).click();
    }

    @Step("Заполнить форму регистрации")
    public void fillOutRegistrationForm(String name, String email, String password) {
        setNameField(name);
        setEmailField(email);
        setPasswordField(password);
    }

    public boolean isWrongPasswordErrorVisible() {
        return !driver.findElements(wrongPasswordError).isEmpty();
    }
}