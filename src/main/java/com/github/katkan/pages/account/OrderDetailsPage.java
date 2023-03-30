package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderDetailsPage extends BasePage {

    private By orderIdLocator = By.cssSelector(".order-number");
    private By orderDateLocator = By.cssSelector(".order-date");

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getOrderId(){
        return driver.findElement(orderIdLocator).getText();
    }

    public String getOrderDate(){
        return driver.findElement(orderDateLocator).getText();
    }
}
