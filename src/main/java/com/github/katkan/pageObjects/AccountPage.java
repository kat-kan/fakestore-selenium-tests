package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private By myOrdersButtonLocator = By.cssSelector("[class$='--orders']");
    private By deleteButtonLocator = By.cssSelector(".delete-me");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void deleteAccount(){
        driver.findElement(deleteButtonLocator).click();
        driver.switchTo().alert().accept();
    }
}
