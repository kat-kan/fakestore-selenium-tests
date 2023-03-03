package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends BasePage{

    protected HeaderPage(WebDriver driver) {
        super(driver);
    }

    private By totalPriceLocator = By.cssSelector("a.cart-contents");
    private By myAccountLocator = By.cssSelector("#menu-item-201");

    public CartPage viewCart(){
        driver.findElement(totalPriceLocator).click();
        return new CartPage(driver);
    }

    public AccountPage viewMyAccount(){
        driver.findElement(myAccountLocator).click();
        return new AccountPage(driver);
    }
}
