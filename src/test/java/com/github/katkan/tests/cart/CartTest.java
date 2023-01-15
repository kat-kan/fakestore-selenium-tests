package com.github.katkan.tests.cart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver1.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.navigate().to("https://fakestore.testelka.pl/");
        By cookieConsentCBar = By.cssSelector(".woocommerce-store-notice__dismiss-link");
        driver.findElement(cookieConsentCBar).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Verify adding product to cart from product page")
    void addTripToCart() {
        driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");

        By addToCartButton = By.cssSelector(".single_add_to_cart_button");
        driver.findElement(addToCartButton).click();
        By productAddedAlert = By.cssSelector(".woocommerce-message");
        wait.until(ExpectedConditions.presenceOfElementLocated(productAddedAlert));

        WebElement cartButtonInAlertMessage = driver.findElement(By.cssSelector(".woocommerce-message .wc-forward"));
        Assertions.assertTrue(cartButtonInAlertMessage.isDisplayed());
    }
}
