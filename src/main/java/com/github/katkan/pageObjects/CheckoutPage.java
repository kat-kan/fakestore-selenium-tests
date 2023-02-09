package com.github.katkan.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    private WebDriverWait wait;

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
        wait = new WebDriverWait(driver, 7);
    }

    public CheckoutPage fillFirstNameField(String firstName){
        //TODO check if for logged in user clear is needed
        wait.until(ExpectedConditions.presenceOfElementLocated(firstNameFieldLocator));
        driver.findElement(firstNameFieldLocator).sendKeys(firstName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillLastNameField(String lastName){
        //TODO check if for logged in user clear is needed
        driver.findElement(lastNameFieldLocator).sendKeys(lastName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCountryField(String country){
        //TODO check if for logged in user clear is needed
        WebElement selectElement = driver.findElement(countryFieldLocator);
        Select select = new Select(selectElement);
        select.selectByVisibleText(country);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillAddressField(String address){
        //TODO check if for logged in user clear is needed
        driver.findElement(addressFieldLocator).sendKeys(address);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillPostcodeField(String postcode){
        //TODO check if for logged in user clear is needed
        driver.findElement(postcodeFieldLocator).sendKeys(postcode);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCityField(String city){
        //TODO check if for logged in user clear is needed
        driver.findElement(cityFieldLocator).sendKeys(city);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillEmailField(String email){
        //TODO check if for logged in user clear is needed
        driver.findElement(emailFieldLocator).sendKeys(email);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillPhoneField(String phone){
        //TODO check if for logged in user clear is needed
        driver.findElement(phoneNumberFieldLocator).sendKeys(phone);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardNumberField(String cardNumber){
        WebElement stripeFrameElement = driver.findElement(By.cssSelector("#stripe-card-element iframe"));
        driver.switchTo().frame(stripeFrameElement);
        //TODO fix other methods and switch to parentFrame after
        driver.findElement(cardNumberLocator).sendKeys(cardNumber);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardExpiryDateField(String cardExpiryDate){
        driver.findElement(cardExpiryDateLocator).sendKeys(cardExpiryDate);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardCvcField(String cardCvc){
        driver.findElement(cardCvcLocator).sendKeys(cardCvc);
        return new CheckoutPage(driver);
    }

    public CheckoutPage login(){
        //TODO implement method
        return new CheckoutPage(driver);
    }

    public CheckoutPage acceptTermsAndConditions(){
        //TODO implement method
        driver.findElement(termsCheckboxLocator).click();
        return new CheckoutPage(driver);
    }

    public OrderReceivedPage confirm() {
        //TODO implement method
        driver.findElement(placeOrderButtonLocator).click();
        return new OrderReceivedPage(driver);
    }
}
