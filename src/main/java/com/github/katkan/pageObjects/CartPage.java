package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage extends BasePage {

    private final WebDriverWait wait;

    private String removeProductButtonCssSelector = "[data-product_id='<product_id>']";
    private By productQuantityLocator = By.cssSelector("[id^='quantity']");
    private By productLinkInCartLocator = By.cssSelector("td.product-name a");
    private By cartItemLocator = By.cssSelector(".cart_item");
    private By loadingWheelLocator = By.cssSelector(".blockUI");
    private By updateCartButtonLocator = By.name("update_cart");
    private By removeButtonLocator = By.cssSelector(".remove");
    private By cartEmptyMessageLocator = By.cssSelector(".cart-empty");
    private By checkoutButtonLocator = By.cssSelector(".checkout-button");


    public CartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
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

    public void changeQuantity(int quantity) {
        WebElement quantityField = driver.findElement(productQuantityLocator);
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(quantity));
        driver.findElement(updateCartButtonLocator).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheelLocator, 0));
    }

    public void removeProduct() {
        driver.findElement(removeButtonLocator).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheelLocator, 0));
    }

    public boolean isCartEmpty() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartEmptyMessageLocator));
        return driver.findElement(cartEmptyMessageLocator).isDisplayed();
    }

    public OrderPage goToCheckout(){
        driver.findElement(checkoutButtonLocator);
        return new OrderPage(driver);
    }
    private By getProductSelector(String id) {
        return By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", id));
    }
}
