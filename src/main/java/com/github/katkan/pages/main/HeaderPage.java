package com.github.katkan.pages.main;

import com.github.katkan.pages.account.AccountPage;
import com.github.katkan.pages.cart.CartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends BasePage {

    @FindBy(css = "a.cart-contents")
    private WebElement totalPriceInCart;

    @FindBy(css = "#menu-item-201")
    private WebElement myAccount;

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public CartPage viewCart(){
        totalPriceInCart.click();
        return new CartPage(driver);
    }

    public AccountPage viewMyAccount(){
        myAccount.click();
        return new AccountPage(driver);
    }
}
