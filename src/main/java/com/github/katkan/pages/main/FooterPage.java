package com.github.katkan.pages.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterPage extends BasePage {

    private By cookieConsentBarLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    public void closeCookieConsentBar() {
        driver.findElement(cookieConsentBarLocator).click();
    }
}
