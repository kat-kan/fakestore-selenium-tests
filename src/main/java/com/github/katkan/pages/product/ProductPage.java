package com.github.katkan.pages.product;

import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.main.FooterPage;
import com.github.katkan.pages.main.HeaderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    public HeaderPage header;
    public FooterPage footer;

    private By viewCartButtonLocator = By.cssSelector(".woocommerce-message .wc-forward");
    private By addToCartButtonLocator = By.cssSelector(".single_add_to_cart_button");
    private By quantityFieldLocator = By.cssSelector("[id^='quantity']");


    public ProductPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        footer = new FooterPage(driver);
    }

    public ProductPage goTo(String productUrl) {
        driver.navigate().to(productUrl);
        return new ProductPage(driver);
    }

    public ProductPage addToCart() {
        driver.findElement(addToCartButtonLocator).click();
        return new ProductPage(driver);
    }

    public ProductPage addToCart(int amount) {
        changeQuantity(amount);
        driver.findElement(addToCartButtonLocator).click();
        return new ProductPage(driver);
    }

    public CartPage viewCart() {
        driver.findElement(viewCartButtonLocator).click();
        return new CartPage(driver);
    }

    private void changeQuantity(int amount) {
        WebElement quantityField = driver.findElement(quantityFieldLocator);
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(amount));
    }
}
