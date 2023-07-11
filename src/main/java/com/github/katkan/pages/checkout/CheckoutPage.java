package com.github.katkan.pages.checkout;

import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.order.OrderReceivedPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CheckoutPage extends BasePage {

    public CardPage cardPage;

    @FindBy(css = "#billing_first_name")
    private WebElement firstNameField;

    @FindBy(css = "#billing_last_name")
    private WebElement lastNameField;

    @FindBy(css = "#billing_country")
    private WebElement countryField;

    @FindBy(css = "#billing_address_1")
    private WebElement addressField;

    @FindBy(css = "#billing_postcode")
    private WebElement postcodeField;

    @FindBy(css = "#billing_city")
    private WebElement cityField;

    @FindBy(css = "#billing_phone")
    private WebElement phoneNumberField;

    @FindBy(css = "#billing_email")
    private WebElement emailField;

    @FindBy(css = "#terms")
    private WebElement termsCheckbox;

    @FindBy(css = "#place_order")
    private WebElement placeOrderButton;

    @FindBy(css = ".showlogin")
    private WebElement showLoginButton;

    @FindBy(css = "#username")
    private WebElement loginField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(css = "[name='login']")
    private WebElement loginButton;

    @FindBy(css = ".blockUI")
    private List<WebElement> loadingWheels;

    @FindBy(css = "#createaccount")
    private WebElement createAccountCheckbox;

    @FindBy(css = "#account_password")
    private WebElement newAccountPasswordField;

    @FindBy(css = ".woocommerce-error")
    private WebElement errorMessage;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        cardPage = new CardPage(driver);
    }

    public CheckoutPage fillFirstNameField(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        provideFieldInputOrKeepExistingOne(firstNameField, firstName);
        return this;
    }

    public CheckoutPage fillLastNameField(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameField));
        provideFieldInputOrKeepExistingOne(lastNameField, lastName);
        return this;
    }

    public CheckoutPage fillCountryField(String country) {
        Select select = new Select(countryField);
        select.selectByVisibleText(country);
        return this;
    }

    public CheckoutPage fillAddressField(String address) {
        provideFieldInputOrKeepExistingOne(addressField, address);
        return this;
    }

    public CheckoutPage fillPostcodeField(String postcode) {
        provideFieldInputOrKeepExistingOne(postcodeField, postcode);
        return this;
    }

    public CheckoutPage fillCityField(String city) {
        provideFieldInputOrKeepExistingOne(cityField, city);
        return this;
    }

    public CheckoutPage fillEmailField(String email) {
        provideFieldInputOrKeepExistingOne(emailField, email);
        return this;
    }

    public CheckoutPage fillPhoneField(String phone) {
        provideFieldInputOrKeepExistingOne(phoneNumberField, phone);
        return this;
    }

    public CheckoutPage login(String login, String password) {
        showLoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(loginField));
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginButton.click();
        return this;
    }

    public CheckoutPage createNewAccount(String password) {
        createAccountCheckbox.click();
        wait.until(ExpectedConditions.visibilityOf(newAccountPasswordField));
        newAccountPasswordField.sendKeys(password);
        return this;
    }

    public CheckoutPage acceptTermsAndConditions() {
        termsCheckbox.click();
        return this;
    }

    public OrderReceivedPage confirm() {
        placeOrderButton.click();
        wait.until(s -> loadingWheels.size() == 0);
        return new OrderReceivedPage(driver);
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

    private void provideFieldInputOrKeepExistingOne(WebElement inputField, String input) {
        if (inputField.getAttribute("value").isBlank()) {
            inputField.sendKeys(input);
        }
    }
}
