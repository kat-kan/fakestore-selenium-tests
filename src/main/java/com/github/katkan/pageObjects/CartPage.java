package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private String removeProductButtonCssSelector = "[data-product_id='<product_id>']";
    private By productQuantityLocator = By.cssSelector(".quantity input");
    private By productLinkInCartLocator = By.cssSelector("td.product-name a");
    private By cartItemLocator = By.cssSelector(".cart_item");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getProductQuantity() {
        return Integer.parseInt(driver.findElement(productQuantityLocator).getAttribute("value"));
    }

    public int getNumberOfProducts() {
        return driver.findElements(cartItemLocator).size();
    }

    public String getProductLink() {
        return driver.findElement(productLinkInCartLocator).getAttribute("href");
    }

    public List<WebElement> getAllProductsLinks() {
        return driver.findElements(productLinkInCartLocator);
    }

    public boolean isProductDisplayed(String id) {
        return driver.findElement(getProductSelector(id)).isDisplayed();
    }

    private By getProductSelector(String id) {
        return By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", id));
    }
}
