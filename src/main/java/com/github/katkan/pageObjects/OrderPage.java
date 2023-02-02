package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage extends BasePage {

    private WebDriver driver;

    private By firstNameFieldLocator = By.cssSelector("#billing_first_name");
    private By lastNameFieldLocator = By.cssSelector("#billing_last_name");
    private By companyFieldLocator = By.cssSelector("#billing_company");
    private By countryFieldLocator = By.cssSelector("#billing_country");
    private By addressFieldLocator = By.cssSelector("#billing_address_1");
    private By postcodeFieldLocator = By.cssSelector("#billing_postcode");
    private By cityFieldLocator = By.cssSelector("#billing_city");
    private By phoneNumberFieldLocator = By.cssSelector("#billing_phone");
    private By emailFieldLocator = By.cssSelector("#billing_email");

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public OrderPage goTo(String url) {
        driver.navigate().to(url);
        return new OrderPage(driver);
    }
}
