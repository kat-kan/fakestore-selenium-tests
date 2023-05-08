package com.github.katkan.pages.order;

import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.main.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {

    public HeaderPage header;

    private WebDriverWait wait;

    @FindBy(css = ".woocommerce-thankyou-order-received")
    private WebElement successMessage;

    @FindBy(css = ".woocommerce-order .order strong")
    private WebElement id;

    @FindBy(css = ".woocommerce-order .date strong")
    private WebElement date;

    @FindBy(css = ".woocommerce-order .total strong")
    private WebElement totalPrice;

    @FindBy(css = ".woocommerce-order .method strong")
    private WebElement paymentMethod;

    @FindBy(css = ".order_item a")
    private WebElement productNames;

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public WebElement isOrderSuccessfullyFinished() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage));
    }

    public String getId() {
        return id.getText();
    }

    public String getDate() {
        return date.getText();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public String getPaymentMethod() {
        return paymentMethod.getText();
    }
}
