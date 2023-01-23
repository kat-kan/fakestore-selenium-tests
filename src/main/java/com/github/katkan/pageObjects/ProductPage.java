package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
    private WebDriver driver;

    private By cartButton = By.cssSelector(".woocommerce-message .wc-forward");
    private By addToCartButton = By.cssSelector(".single_add_to_cart_button");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductPage goTo(String productUrl) {
        driver.navigate().to(productUrl);
        return new ProductPage(driver);
    }

    public ProductPage addToCart() {
        driver.findElement(addToCartButton).click();
        return new ProductPage(driver);
    }

    public CartPage viewCart(){
        driver.findElement(cartButton).click();
        return new CartPage(driver);
    }
}
