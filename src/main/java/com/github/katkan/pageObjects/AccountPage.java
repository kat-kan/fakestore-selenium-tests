package com.github.katkan.pageObjects;

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
    }

    public MyOrdersPage viewOrders(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myOrdersButtonLocator));
        driver.findElement(myOrdersButtonLocator).click();
        return new MyOrdersPage(driver);
    }
}
