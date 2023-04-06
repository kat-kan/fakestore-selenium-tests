package com.github.katkan.pages.account;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {

    private WebDriverWait wait;

    @FindBy(css = "[class$='--orders']")
    WebElement myOrdersButton;

    @FindBy(css = ".delete-me")
    WebElement deleteButton;

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
        PageFactory.initElements(driver, this);
    }

    public void deleteAccount() {
        wait.until(ExpectedConditions.visibilityOf(deleteButton));
        deleteButton.click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlToBe("https://fakestore.testelka.pl/"));
    }

    public MyOrdersPage viewOrders() {
        wait.until(ExpectedConditions.visibilityOf(myOrdersButton));
        myOrdersButton.click();
        return new MyOrdersPage(driver);
    }
}
