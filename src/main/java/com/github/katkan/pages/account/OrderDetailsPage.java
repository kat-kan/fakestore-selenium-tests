package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderDetailsPage extends BasePage {

    @FindBy(css = ".order-number")
    WebElement orderId;

    @FindBy(css = ".order-date")
    WebElement orderDate;

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getOrderId() {
        return orderId.getText();
    }

    public String getOrderDate() {
        return orderDate.getText();
    }
}
