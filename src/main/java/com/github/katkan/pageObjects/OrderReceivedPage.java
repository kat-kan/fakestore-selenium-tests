package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderReceivedPage extends BasePage {

    private By orderReceivedMessageLocator = By.cssSelector(".woocommerce-thankyou-order-received");

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOrderSuccessfullyFinished(){
        WebElement orderReceivedMessage = driver.findElement(orderReceivedMessageLocator);
        return orderReceivedMessage.isDisplayed();
    }
}
