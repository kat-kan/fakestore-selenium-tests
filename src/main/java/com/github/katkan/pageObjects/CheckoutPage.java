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
    private By loginButtonLocator = By.cssSelector("[name='login']");
    private By loadingWheelLocator = By.cssSelector(".blockUI");
    private By stripeFrameLocator = By.cssSelector("#stripe-card-element iframe");
    private By stripeCvcFrameLocator = By.cssSelector("#stripe-cvc-element iframe");
    private By stripeExpiryDateFrameLocator = By.cssSelector("#stripe-exp-element iframe");
    private By createAccountCheckboxLocator = By.cssSelector("#createaccount");
    private By newAccountPasswordFieldLocator = By.cssSelector("#account_password");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public CheckoutPage fillFirstNameField(String firstName){
        wait.until(ExpectedConditions.presenceOfElementLocated(firstNameFieldLocator));
        provideFieldInputOrKeepExistingOne(firstNameFieldLocator, firstName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillLastNameField(String lastName){
        wait.until(ExpectedConditions.presenceOfElementLocated(lastNameFieldLocator));
        provideFieldInputOrKeepExistingOne(lastNameFieldLocator, lastName);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCountryField(String country){
        WebElement selectElement = driver.findElement(countryFieldLocator);
        Select select = new Select(selectElement);
        select.selectByVisibleText(country);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillAddressField(String address){
        provideFieldInputOrKeepExistingOne(addressFieldLocator, address);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillPostcodeField(String postcode){
        provideFieldInputOrKeepExistingOne(postcodeFieldLocator, postcode);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCityField(String city){
        provideFieldInputOrKeepExistingOne(cityFieldLocator, city);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillEmailField(String email){
        provideFieldInputOrKeepExistingOne(emailFieldLocator, email);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillPhoneField(String phone){
        provideFieldInputOrKeepExistingOne(phoneNumberFieldLocator, phone);
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardNumberField(String cardNumber){
        wait.until(ExpectedConditions.presenceOfElementLocated(stripeFrameLocator));
        WebElement stripeFrameElement = driver.findElement(stripeFrameLocator);
        driver.switchTo().frame(stripeFrameElement);
        driver.findElement(cardNumberLocator).sendKeys(cardNumber);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardExpiryDateField(String cardExpiryDate){
        wait.until(ExpectedConditions.presenceOfElementLocated(stripeExpiryDateFrameLocator));
        WebElement stripeFrameElement = driver.findElement(stripeExpiryDateFrameLocator);
        driver.switchTo().frame(stripeFrameElement);
        driver.findElement(cardExpiryDateLocator).sendKeys(cardExpiryDate);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardCvcField(String cardCvc){
        wait.until(ExpectedConditions.presenceOfElementLocated(stripeCvcFrameLocator));
        WebElement stripeFrameElement = driver.findElement(stripeCvcFrameLocator);
        driver.switchTo().frame(stripeFrameElement);
        driver.findElement(cardCvcLocator).sendKeys(cardCvc);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }

    public CheckoutPage login(String login, String password){
        driver.findElement(showLoginButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFieldLocator));
        driver.findElement(loginFieldLocator).sendKeys(login);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheelLocator, 0));
        return new CheckoutPage(driver);
    }

    public CheckoutPage createNewAccount(String password){
        driver.findElement(createAccountCheckboxLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(newAccountPasswordFieldLocator));
        driver.findElement(newAccountPasswordFieldLocator).sendKeys(password);
        return new CheckoutPage(driver);
    }

    public CheckoutPage acceptTermsAndConditions(){
        driver.findElement(termsCheckboxLocator).click();
        return new CheckoutPage(driver);
    }

    public OrderReceivedPage confirm() {
        driver.findElement(placeOrderButtonLocator).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheelLocator, 0));
        return new OrderReceivedPage(driver);
    }

    private void provideFieldInputOrKeepExistingOne(By fieldLocator, String input) {
        WebElement inputField = driver.findElement(fieldLocator);
        if (inputField.getAttribute("value").isBlank()){
            inputField.sendKeys(input);
        }
    }
}
