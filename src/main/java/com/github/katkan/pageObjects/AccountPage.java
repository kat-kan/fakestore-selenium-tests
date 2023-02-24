package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private By myOrdersButton = By.cssSelector("[class$='--orders']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }
}
