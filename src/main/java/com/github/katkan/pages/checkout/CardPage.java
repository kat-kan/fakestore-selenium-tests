package com.github.katkan.pages.checkout;

import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CardPage extends BasePage {

    @FindBy(css = "#stripe-card-element iframe")
    private WebElement stripeCardNumberFrame;

    @FindBy(css = ".InputElement[name='cardnumber']")
    private WebElement cardNumberField;

    @FindBy(css = "#stripe-exp-element iframe")
    private WebElement stripeExpiryDateFrame;

    @FindBy(css = "[name='exp-date']")
    private WebElement cardExpiryDate;

    @FindBy(css = "#stripe-cvc-element iframe")
    private WebElement stripeCvcFrame;

    @FindBy(css = "[name='cvc']")
    private WebElement cardCvc;

    public CardPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillCardNumberField(String cardNumber){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeCardNumberFrame));
        cardNumberField.sendKeys(cardNumber);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardExpiryDateField(String expiryDate){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeExpiryDateFrame));
        cardExpiryDate.sendKeys(expiryDate);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }

    public CheckoutPage fillCardCvcField(String cvc) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeCvcFrame));
        cardCvc.sendKeys(cvc);
        driver.switchTo().parentFrame();
        return new CheckoutPage(driver);
    }
}
