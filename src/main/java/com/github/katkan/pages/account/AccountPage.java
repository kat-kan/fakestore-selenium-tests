package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.tables.my_orders.MyOrdersTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {

    private WebDriverWait wait;

    private By myOrdersButtonLocator = By.cssSelector("[class$='--orders']");
    private By deleteButtonLocator = By.cssSelector(".delete-me");

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public void deleteAccount(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonLocator));
        driver.findElement(deleteButtonLocator).click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlToBe("https://fakestore.testelka.pl/"));
    }

/*    public MyOrdersPage viewOrders(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myOrdersButtonLocator));
        driver.findElement(myOrdersButtonLocator).click();
        return new MyOrdersPage(driver);
    }*/

    public MyOrdersTable viewOrders(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myOrdersButtonLocator));
        driver.findElement(myOrdersButtonLocator).click();
        return new MyOrdersTable(driver);
    }
}
