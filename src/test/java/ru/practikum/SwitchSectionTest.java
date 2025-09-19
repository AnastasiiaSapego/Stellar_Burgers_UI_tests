package ru.practikum;

import io.qameta.allure.*;
import org.junit.Assert;
import org.junit.Test;
import ru.practikum.pageobject.MainPageWithoutSignIn;

@Epic("UI")
@Feature("Конструктор")
public class SwitchSectionTest extends BaseTest {

    @Test
    @Story("Переход к разделу 'Булки'")
    public void bunsTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickBunSection();
        Assert.assertEquals("Булки", main.getSelectedSectionText());
    }

    @Test
    @Story("Переход к разделу 'Соусы'")
    public void saucesTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSauceSection();
        Assert.assertEquals("Соусы", main.getSelectedSectionText());
    }

    @Test
    @Story("Переход к разделу 'Начинки'")
    public void fillingsTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickFillingSection();
        Assert.assertEquals("Начинки", main.getSelectedSectionText());
    }
}
