package ru.practikum;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import ru.practikum.api.*;
import ru.practikum.pageobject.*;

import static org.apache.http.HttpStatus.*;

@Epic("UI")
@Feature("Вход в аккаунт")
public class SignInTest extends BaseTest {

    private User user;
    private String token;

    @Before
    public void setupUser() {
        user = UserClient.randomUser();
        UserClient.register(user).statusCode(SC_OK);

        token = UserClient.extractAccessToken(UserClient.login(user));
        Assert.assertNotNull("Не удалось получить accessToken тестового пользователя", token);
    }

    @After
    public void cleanupUser() {
        if (token != null) {
            UserClient.delete(token).statusCode(SC_ACCEPTED);
        }
    }

    private void uiLogin() {
        SignInForm signIn = new SignInForm(driver);
        signIn.waitOpened();
        signIn.fillOutSignInForm(user.email, user.password);
        signIn.clickSignInButtonInSignInForm();

        MainPageWithSignIn main = new MainPageWithSignIn(driver);
        Assert.assertEquals(
                "Ожидали на странице кнопку 'Оформить заказ'",
                "Оформить заказ",
                main.waitAndGetMakeOrderButtonText()
        );
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginFromMainButton() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickSignInButtonInMainPage();
        uiLogin();
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginFromHeaderPersonalAccount() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickPersonalAccButton();
        uiLogin();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginFromRegistrationForm() {
        driver.get(MainPageWithoutSignIn.URL);
        new MainPageWithoutSignIn(driver).clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickRegistrationButtonInSignInForm();
        new RegistrationForm(driver).clickSignInButtonInRegistrationForm();

        uiLogin();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
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