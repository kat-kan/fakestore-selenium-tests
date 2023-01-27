package com.github.katkan.tests.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver driver;

    private By cookieConsentBarLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver1.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
