package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterPage extends BasePage {

    private By cookieConsentBarLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    protected FooterPage(WebDriver driver) {
        super(driver);
    }

    public void closeCookieConsentBar() {
        driver.findElement(cookieConsentBarLocator).click();
    }
}
