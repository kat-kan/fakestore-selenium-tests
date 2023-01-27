package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{

    private By productQuantityLocator = By.cssSelector(".quantity input");
    private By productLinkInCartLocator = By.cssSelector("td.product-name a");
    String removeProductButtonCssSelector = "[data-product_id='<product_id>']";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getProductQuantity() {
        return Integer.parseInt(driver.findElement(productQuantityLocator).getAttribute("value"));
    }

    public String getProductLink() {
        return driver.findElement(productLinkInCartLocator).getAttribute("href");
    }

    public boolean isProductDisplayed(String id) {
        return driver.findElement(getProductSelector(id)).isDisplayed();
    }

    private By getProductSelector(String id) {
        return By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", id));
    }
}
