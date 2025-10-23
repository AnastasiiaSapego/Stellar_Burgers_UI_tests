package ru.practikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPageWithoutSignIn extends BasePage {
    public static final String URL = "https://stellarburgers.nomoreparties.site/";

    private final By personalAccLink =
            By.xpath("//header//a[contains(@href,'/account')]");

    private final By signInButtonInMainPage =
            By.xpath("//main//button[normalize-space()='Войти в аккаунт']");

    private final By bunTab     =
            By.xpath("//span[normalize-space()='Булки']/parent::div[contains(@class,'tab_tab__')]");
    private final By sauceTab   =
            By.xpath("//span[normalize-space()='Соусы']/parent::div[contains(@class,'tab_tab__')]");
    private final By fillingTab =
            By.xpath("//span[normalize-space()='Начинки']/parent::div[contains(@class,'tab_tab__')]");

    private final By bunSelected =
            By.xpath("//span[normalize-space()='Булки']/parent::div[contains(@class,'tab_tab_type_current__')]");
    private final By sauceSelected =
            By.xpath("//span[normalize-space()='Соусы']/parent::div[contains(@class,'tab_tab_type_current__')]");
    private final By fillingSelected =
            By.xpath("//span[normalize-space()='Начинки']/parent::div[contains(@class,'tab_tab_type_current__')]");

    private final By selectedSection = By.cssSelector("div[class*='tab_tab_type_current__']");

    private final By modalOverlay = By.cssSelector("div[class*='Modal_modal_overlay']");

    public MainPageWithoutSignIn(WebDriver driver) { super(driver); }

    @Step("Клик по 'Личный кабинет'")
    public void clickPersonalAccButton() { jsClick(personalAccLink); }

    @Step("Клик по 'Войти в аккаунт' на главной")
    public void clickSignInButtonInMainPage() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        jsClick(signInButtonInMainPage);
    }

    private void selectTab(By tab, By tabSelected) {
        jsClick(tab);
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabSelected));
    }

    @Step("Перейти в раздел 'Булки'")
    public void clickBunSection() {
        selectTab(bunTab, bunSelected);
    }

    @Step("Перейти в раздел 'Соусы'")
    public void clickSauceSection() {
        selectTab(sauceTab, sauceSelected);
    }

    @Step("Перейти в раздел 'Начинки'")
    public void clickFillingSection() {
        selectTab(fillingTab, fillingSelected);
    }

    @Step("Текст активного раздела конструктора")
    public String getSelectedSectionText() {
        return waitVisible(selectedSection).getText();
    }
}
