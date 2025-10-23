package ru.practikum;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.practikum.api.User;
import ru.practikum.api.UserClient;
import ru.practikum.pageobject.*;
import static org.apache.http.HttpStatus.*;

@Epic("UI")
@Feature("Регистрация")
public class RegistrationTest extends BaseTest {

    private static final String EXPECTED_ORDER_BUTTON_TEXT = "Оформить заказ";

    private User user;
    private String accessToken;

    @After
    public void deleteUserIfCreated() {
        if (user != null) {
            accessToken = UserClient.extractAccessToken(UserClient.login(user));
            if (accessToken != null) {
                UserClient.delete(accessToken).statusCode(SC_ACCEPTED);
            }
        }
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void checkSuccessRegistration() {
        user = UserClient.randomUser();

        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickRegistrationButtonInSignInForm();

        RegistrationForm reg = new RegistrationForm(driver);
        reg.waitOpened();
        reg.fillOutRegistrationForm(user.name, user.email, user.password);
        reg.clickRegistrationButton();

        signIn.fillOutSignInForm(user.email, user.password);
        signIn.clickSignInButtonInSignInForm();

        MainPageWithSignIn mainAfter = new MainPageWithSignIn(driver);
        String actual = mainAfter.waitAndGetMakeOrderButtonText();

        Assert.assertEquals("Ожидаем текст кнопки 'Оформить заказ'", EXPECTED_ORDER_BUTTON_TEXT, actual);
    }

    @Test
    @DisplayName("Ошибка при коротком пароле")
    @Description("Пароль короче 6 символов — должна появиться ошибка")
    public void registrationWrongPassword() {
        driver.get(MainPageWithoutSignIn.URL);

        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickRegistrationButtonInSignInForm();

        RegistrationForm reg = new RegistrationForm(driver);
        reg.fillOutRegistrationForm("Name", "mail" + RandomStringUtils.randomAlphanumeric(6) + "@ya.ru", "123");
        reg.clickRegistrationButton();

        Assert.assertTrue("Ожидали сообщение 'Некорректный пароль'", reg.isWrongPasswordErrorVisible());
    }
}