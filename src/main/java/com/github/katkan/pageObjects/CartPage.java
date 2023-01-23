package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    private By quantityField = By.cssSelector(".quantity input");
    private By productLinkInCart = By.cssSelector("td.product-name a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getProductQuantity() {
        return Integer.parseInt(driver.findElement(quantityField).getAttribute("value"));
    }

    public String getProductLink() {
        return driver.findElement(productLinkInCart).getAttribute("href");
    }
}
