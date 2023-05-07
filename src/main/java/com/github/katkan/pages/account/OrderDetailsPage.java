package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderDetailsPage extends BasePage {

    @FindBy(css = ".order-number")
    WebElement orderId;

    @FindBy(css = ".order-date")
    WebElement orderDate;

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getId() {
        return orderId.getText();
    }

    public String getDate() {
        return orderDate.getText();
    }
}
