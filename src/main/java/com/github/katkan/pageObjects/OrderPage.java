package com.github.katkan.pageObjects;

import org.openqa.selenium.WebDriver;

public class OrderPage extends BasePage {

    private WebDriver driver;

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public OrderPage goTo(String url) {
        driver.navigate().to(url);
        return new OrderPage(driver);
    }
}
