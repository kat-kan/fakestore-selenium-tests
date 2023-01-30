package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage extends BasePage {

    public FooterPage footer;
    private WebDriverWait wait;

    private By viewCartButtonLocator = By.cssSelector(".added_to_cart");
    private String addToCartButtonLocator = "[data-product_id='<product_id>']";

    public CategoryPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, 5);
        footer = new FooterPage(driver);
    }

    public CategoryPage goTo(String url) {
        driver.navigate().to(url);
        return new CategoryPage(driver);
    }

    public CategoryPage addToCart(String id) {
        driver.findElement(getProductSelector(id)).click();
        return new CategoryPage(driver);
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButtonLocator));
        driver.findElement(viewCartButtonLocator).click();
        return new CartPage(driver);
    }

    private By getProductSelector(String id) {
        return By.cssSelector(addToCartButtonLocator.replace("<product_id>", id));
    }
}
