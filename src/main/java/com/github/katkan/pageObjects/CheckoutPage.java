package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameFieldLocator = By.cssSelector("#billing_first_name");
    private By lastNameFieldLocator = By.cssSelector("#billing_last_name");
    private By countryFieldLocator = By.cssSelector("#billing_country");
    private By addressFieldLocator = By.cssSelector("#billing_address_1");
    private By postcodeFieldLocator = By.cssSelector("#billing_postcode");
    private By cityFieldLocator = By.cssSelector("#billing_city");
    private By phoneNumberFieldLocator = By.cssSelector("#billing_phone");
    private By emailFieldLocator = By.cssSelector("#billing_email");
    private By cardNumberLocator = By.cssSelector(".InputElement[name='cardnumber']");
    private By cardExpiryDateLocator = By.cssSelector("[name='exp-date']");
    private By cardCvcLocator = By.cssSelector("[name='cvc']");
    private By termsCheckboxLocator = By.cssSelector("#terms");
    private By placeOrderButtonLocator = By.cssSelector("#place_order");
    private By showLoginButtonLocator = By.cssSelector(".showlogin");
    private By loginFieldLocator = By.cssSelector("#username");
    private By passwordFieldLocator = By.cssSelector("#password");
    private By loadingWheelLocator = By.cssSelector(".blockUI");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage goTo(String url) {
        driver.navigate().to(url);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillOrderData() {
        //TODO implement method
        return new CheckoutPage(driver);
    }

    public CheckoutPage addFirstName(String firstName){
        //TODO check if for logged in user clear is needed
        driver.findElement(firstNameFieldLocator).sendKeys(firstName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addLastName(String lastName){
        //TODO check if for logged in user clear is needed
        driver.findElement(lastNameFieldLocator).sendKeys(lastName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addCountry(String country){
        //TODO check if for logged in user clear is needed
        driver.findElement(countryFieldLocator).sendKeys(country);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addAddress(String address){
        //TODO check if for logged in user clear is needed
        driver.findElement(addressFieldLocator).sendKeys(address);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addPostcode(String postcode){
        //TODO check if for logged in user clear is needed
        driver.findElement(postcodeFieldLocator).sendKeys(postcode);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addCity(String city){
        //TODO check if for logged in user clear is needed
        driver.findElement(cityFieldLocator).sendKeys(city);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addEmail(String email){
        //TODO check if for logged in user clear is needed
        driver.findElement(emailFieldLocator).sendKeys(email);
        return new CheckoutPage(driver);
    }

    public CheckoutPage addPhone(String phone){
        //TODO check if for logged in user clear is needed
        driver.findElement(phoneNumberFieldLocator).sendKeys(phone);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillPaymentData() {
        //TODO implement method
        return new CheckoutPage(driver);
    }

    public CheckoutPage login(){
        //TODO implement method
        return new CheckoutPage(driver);
    }

    public CheckoutPage acceptTermsAndConditions(){
        //TODO implement method
        return new CheckoutPage(driver);
    }

    public void confirm() {
        //TODO implement method
    }
}
