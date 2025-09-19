package ru.practikum;

import io.qameta.allure.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.practikum.pageobject.*;

@Epic("UI")
@Feature("Регистрация")
public class RegistrationTest extends BaseTest {

    private static final String EXPECTED_ORDER_BUTTON_TEXT = "Оформить заказ";

    @Test
    @Story("Успешная регистрация нового пользователя")
    @Description("Заполняем форму регистрации данными и логинимся")
    public void checkSuccessRegistration() {
        String name = "User" + RandomStringUtils.randomAlphabetic(6);
        String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(8);

        driver.get(MainPageWithoutSignIn.URL);

        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSignInButtonInMainPage();

        SignInForm signIn = new SignInForm(driver);
        signIn.clickRegistrationButtonInSignInForm();

        RegistrationForm reg = new RegistrationForm(driver);
        reg.fillOutRegistrationForm(name, email, password);
        reg.clickRegistrationButton();

        signIn.fillOutSignInForm(email, password);
        signIn.clickSignInButtonInSignInForm();

        MainPageWithSignIn mainAfter = new MainPageWithSignIn(driver);
        String actual = mainAfter.waitAndGetMakeOrderButtonText();

        Assert.assertEquals("Ожидаем текст кнопки 'Оформить заказ'", EXPECTED_ORDER_BUTTON_TEXT, actual);
    }

    @Test
    @Story("Ошибка при коротком пароле")
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