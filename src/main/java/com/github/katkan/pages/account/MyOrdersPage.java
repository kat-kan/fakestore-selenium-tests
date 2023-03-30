package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyOrdersPage extends BasePage {

    private By ordersListLocator = By.cssSelector("woocommerce-orders-table__row");

    public MyOrdersPage(WebDriver driver) {
        super(driver);
    }

    public OrderDetailsPage goToOrderDetails(String orderId){
        driver.findElement(By.partialLinkText(orderId)).click();
        return new OrderDetailsPage(driver);
    }
}
