package com.github.katkan.pages.cart;

import com.github.katkan.pages.checkout.CheckoutPage;
import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.tables.cart.CartItemTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "td.product-name a")
    private WebElement productLinkInCart;

    @FindBy(css = "[id^='quantity']")
    private WebElement productQuantity;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".blockUI")
    private WebElement loadingWheel;

    @FindBy(css = "[name=update_cart]")
    private WebElement updateCartButton;

    @FindBy(css = ".remove")
    private WebElement removeButton;

    @FindBy(css = ".cart-empty")
    private WebElement cartEmptyMessage;

    @FindBy(css = ".checkout-button")
    private WebElement checkoutButton;

    @FindBy(css = ".order-total .amount bdi")
    private WebElement orderTotalPrice;

    private String removeProductButtonCssSelector = "[data-product_id='<product_id>']";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartItemTable getCartItemsTable() {
        return new CartItemTable(driver);
    }

    public int getProductQuantity() {
        return Integer.parseInt(productQuantity.getAttribute("value"));
    }

    public int getNumberOfProducts() {
        return cartItems.size();
    }

    public String getProductLink() {
        return productLinkInCart.getAttribute("href");
    }

    public WebElement isProductDisplayed(String id) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getProductSelector(id)));
    }

    public void changeQuantity(int quantity) {
        productQuantity.clear();
        productQuantity.sendKeys(String.valueOf(quantity));
        updateCartButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loadingWheel));
    }

    public void removeProduct() {
        removeButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loadingWheel));
    }

    public boolean isCartEmpty() {
        wait.until(ExpectedConditions.visibilityOf(cartEmptyMessage));
        return cartEmptyMessage.isDisplayed();
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    public String getTotalPrice() {
        return orderTotalPrice.getText();
    }

    private By getProductSelector(String id) {
        return By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", id));
    }
}
