package com.github.katkan.pages.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterPage extends BasePage {

    @FindBy(css = ".woocommerce-store-notice__dismiss-link")
    private WebElement cookieConsentBar;

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    public void closeCookieConsentBar() {
        cookieConsentBar.click();
    }
}
