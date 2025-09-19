package ru.practikum;

import io.qameta.allure.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import ru.practikum.api.User;
import ru.practikum.api.UserClient;
import ru.practikum.pageobject.*;

@Epic("UI")
@Feature("Вход в аккаунт")
public class SignInTest extends BaseTest {

    private User user;
    private String token;

    @Before
    public void setupUser() {
        user = UserClient.randomUser();
        UserClient.register(user).statusCode(200);
        token = UserClient.loginAndGetToken(user);
    }

    @After
    public void cleanupUser() {
        UserClient.delete(token);
    }

    private void uiLogin() {
        SignInForm signIn = new SignInForm(driver);
        signIn.waitOpened();
        signIn.fillOutSignInForm(user.email, user.password);
        signIn.clickSignInButtonInSignInForm();

        MainPageWithSignIn main = new MainPageWithSignIn(driver);
        Assert.assertEquals("Ожидали на странице кнопку 'Оформить заказ'", "Оформить заказ",
                main.waitAndGetMakeOrderButtonText());
    }

    @Test
    @Story("Вход по кнопке 'Войти в аккаунт' на главной")
    public void loginFromMainButton() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickSignInButtonInMainPage();
        uiLogin();
    }

    @Test
    @Story("Вход через кнопку 'Личный кабинет'")
    public void loginFromHeaderPersonalAccount() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickPersonalAccButton();
        uiLogin();
    }

    @Test
    @Story("Вход через кнопку в форме регистрации")
    public void loginFromRegistrationForm() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickRegistrationButtonInSignInForm();
        new RegistrationForm(driver).clickSignInButtonInRegistrationForm();
        uiLogin();
    }

    @Test
    @Story("Вход через форму восстановления пароля")
    public void loginFromPasswordRecoveryForm() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickForgotPassword();

        PasswordRecoveryForm pr = new PasswordRecoveryForm(driver);
        pr.clickSignInButtonInPasswordRecoveryForm();
        uiLogin();
    }
}
