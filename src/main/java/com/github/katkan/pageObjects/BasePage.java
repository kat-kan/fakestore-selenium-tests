package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    private By cookieConsentBarLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    public void closeCookieConsentBar(){
        driver.findElement(cookieConsentBarLocator).click();
    }
}
