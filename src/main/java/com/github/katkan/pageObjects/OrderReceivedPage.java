package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {

    private WebDriverWait wait;

    private By orderReceivedMessageLocator = By.cssSelector(".woocommerce-thankyou-order-received");

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public WebElement isOrderSuccessfullyFinished(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedMessageLocator));
    }
}
