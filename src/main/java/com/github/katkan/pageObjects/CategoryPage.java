package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By viewCartButton = By.cssSelector(".added_to_cart");

    public CategoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public CategoryPage goTo(String url) {
        driver.navigate().to(url);
        return new CategoryPage(driver, wait);
    }

    public CategoryPage addToCart(String id) {
        driver.findElement(getProductSelector(id)).click();
        return new CategoryPage(driver, wait);
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
        driver.findElement(viewCartButton).click();
        return new CartPage(driver);
    }

    private By getProductSelector(String id) {
        return By.cssSelector("[data-product_id='" + id + "']");
    }

}
