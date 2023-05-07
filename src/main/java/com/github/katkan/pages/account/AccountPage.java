package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.tables.my_orders.MyOrdersTable;
import com.github.katkan.properties.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {

    @FindBy(css = "[class$='--orders']")
    private WebElement myOrdersButton;

    @FindBy(css = ".delete-me")
    private WebElement deleteButton;

    private WebDriverWait wait;

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public void deleteAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlToBe(Properties.getUrl()));
    }

    public MyOrdersTable viewOrders() {
        wait.until(ExpectedConditions.elementToBeClickable(myOrdersButton));
        myOrdersButton.click();
        return new MyOrdersTable(driver);
    }
}
