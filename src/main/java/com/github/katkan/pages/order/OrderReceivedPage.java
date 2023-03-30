package com.github.katkan.pages.order;

import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.main.HeaderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderReceivedPage extends BasePage {

    public HeaderPage header;

    private WebDriverWait wait;

    private By orderReceivedMessageLocator = By.cssSelector(".woocommerce-thankyou-order-received");
    private By orderIdLocator = By.cssSelector(".woocommerce-order .order strong");
    private By orderDateLocator = By.cssSelector(".woocommerce-order .date strong");
    private By orderTotalPriceLocator = By.cssSelector(".woocommerce-order .total strong");
    private By orderPaymentMethodLocator = By.cssSelector(".woocommerce-order .method strong");
    private By orderProductNamesLocator = By.cssSelector(".order_item a");
    private By orderProductQuantitiesLocator = By.cssSelector(".order_item .product-quantity");

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public WebElement isOrderSuccessfullyFinished(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedMessageLocator));
    }

    public String getOrderId(){
        return driver.findElement(orderIdLocator).getText();
    }

    public String getOrderDate(){
        return driver.findElement(orderDateLocator).getText();
    }

    public String getOrderTotalPrice(){
        //TODO check encoding issue
        return driver.findElement(orderTotalPriceLocator).getText();
    }

    public String getPaymentMethod(){
        return driver.findElement(orderPaymentMethodLocator).getText();
    }

    public List<WebElement> getProductNames(){
        return driver.findElements(orderProductNamesLocator);
    }

    public String getCurrentDateInSpecifiedFormat(){
        SimpleDateFormat orderSummaryDateFormat = new SimpleDateFormat("d MMMM, yyyy", Locale.forLanguageTag("pl"));
        return orderSummaryDateFormat.format(new Date());
    }
}
