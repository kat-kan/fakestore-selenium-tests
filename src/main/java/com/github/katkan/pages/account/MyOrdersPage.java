package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MyOrdersPage extends BasePage {

    @FindBy(css = "woocommerce-orders-table__row")
    private List<WebElement> ordersList;

    public MyOrdersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public OrderDetailsPage goToOrderDetails(String orderId) {
        //TODO how to get first row and remain PageFactory???
        //ordersList.click();
        return new OrderDetailsPage(driver);
    }
}
