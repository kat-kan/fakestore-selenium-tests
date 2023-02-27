package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {

    private WebDriverWait wait;

    private By orderReceivedMessageLocator = By.cssSelector(".woocommerce-thankyou-order-received");
    private By orderNumberLocator = By.cssSelector(".woocommerce-order .order strong");
    private By orderDateLocator = By.cssSelector(".woocommerce-order .date strong");
    private By orderTotalPriceLocator = By.cssSelector(".woocommerce-order .total strong");
    private By orderPaymentMethodLocator = By.cssSelector(".woocommerce-order .method strong");
    private By orderProductNamesLocator = By.cssSelector(".order_item a");
    private By orderProductQuantitiesLocator = By.cssSelector(".order_item .product-quantity");

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public WebElement isOrderSuccessfullyFinished(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedMessageLocator));
    }
}
