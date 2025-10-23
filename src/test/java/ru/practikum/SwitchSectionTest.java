package ru.practikum;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import ru.practikum.pageobject.MainPageWithoutSignIn;

@Epic("UI")
@Feature("Конструктор")
public class SwitchSectionTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу 'Булки' (через другой раздел)")
    public void bunsTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSauceSection();
        main.clickBunSection();
        Assert.assertEquals("Булки", main.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void saucesTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickSauceSection();
        Assert.assertEquals("Соусы", main.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void fillingsTab() {
        driver.get(MainPageWithoutSignIn.URL);
        MainPageWithoutSignIn main = new MainPageWithoutSignIn(driver);
        main.clickFillingSection();
        Assert.assertEquals("Начинки", main.getSelectedSectionText());
    }
}
