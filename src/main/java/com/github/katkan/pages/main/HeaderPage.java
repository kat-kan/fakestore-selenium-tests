package com.github.katkan.pages.main;

import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.account.AccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends BasePage {

    public HeaderPage(WebDriver driver) {
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
