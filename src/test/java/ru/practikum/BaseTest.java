package ru.practikum;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Before
    @Step("Инициализация WebDriver")
    public void setUp() {
        driver = DriverFactory.create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().setSize(new Dimension(1280, 900));
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
