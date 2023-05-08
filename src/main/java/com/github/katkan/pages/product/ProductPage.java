package com.github.katkan.pages.product;

import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.main.FooterPage;
import com.github.katkan.pages.main.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    public HeaderPage header;
    public FooterPage footer;

    @FindBy(css = ".woocommerce-message .wc-forward")
    private WebElement viewCartButton;

    @FindBy(css= ".single_add_to_cart_button")
    private WebElement addToCartButton;

    @FindBy(css = "[id^='quantity']")
    private WebElement quantityField;

    @FindBy(css = ".product_title")
    private WebElement productName;

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
        addToCartButton.click();
        return new ProductPage(driver);
    }

    public ProductPage addToCart(int amount) {
        changeQuantity(amount);
        addToCartButton.click();
        return new ProductPage(driver);
    }

    public CartPage viewCart() {
        viewCartButton.click();
        return new CartPage(driver);
    }

    public String getProductName(){
        return productName.getText();
    }

    private void changeQuantity(int amount) {
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(amount));
    }
}
