package com.github.katkan.tests.cart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

    @Test
    @DisplayName("Verify adding product to cart from category page")
    void addTripToCartFromCategoryPageTest() {
        WebElement categoriesElement = driver.findElement(By.cssSelector("div.columns-3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoriesElement);
        WebElement firstCategory = driver.findElement(By.xpath("//li[@class='product-category product first']"));
        firstCategory.click();

        driver.findElement(By.cssSelector("[data-product_id='393']")).click();
        By addedElement = By.cssSelector(".added");
        wait.until(ExpectedConditions.presenceOfElementLocated(addedElement));

        WebElement seeCartButton = driver.findElement(By.cssSelector(".added_to_cart"));
        Assertions.assertTrue(seeCartButton.isDisplayed());
    }

    @Test
    @DisplayName("Verify adding minimum 10 trips to cart")
    void addTrip10TimesToCartTest() {
        WebElement categoriesElement = driver.findElement(By.cssSelector("div.columns-3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoriesElement);
        WebElement firstCategory = driver.findElement(By.xpath("//li[@class='product-category product first']"));
        firstCategory.click();

        for (int i = 0; i < 10; i++) {
            WebElement element = driver.findElement(By.cssSelector(".button[data-product_id='393']"));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            By loadingWheel = By.cssSelector(".loading");
            wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheel, 0));
        }

        WebElement cartTotalPrice = driver.findElement(By.cssSelector(".cart-contents .woocommerce-Price-amount"));
        Assertions.assertEquals("36 000,00 zł", cartTotalPrice.getText());
    }

    @Test
    @DisplayName("Verify adding multiple products to cart from product page")
    void addTripToCartAndModifyAmountTest() {
        driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");

        WebElement quantityField = driver.findElement(By.cssSelector("[id^='quantity']"));
        quantityField.clear();
        String amount = "9";
        quantityField.sendKeys(amount);

        By addToCartButton = By.cssSelector(".single_add_to_cart_button");
        driver.findElement(addToCartButton).click();
        By productAddedAlert = By.cssSelector(".woocommerce-message");
        wait.until(ExpectedConditions.presenceOfElementLocated(productAddedAlert));

        WebElement alert = driver.findElement(By.cssSelector(".woocommerce-message"));
        WebElement cartButtonInAlertMessage = driver.findElement(By.cssSelector(".woocommerce-message .wc-forward"));
        Assertions.assertAll(
                () -> Assertions.assertTrue(cartButtonInAlertMessage.isDisplayed()),
                () -> Assertions.assertTrue(alert.getText().contains(amount))
        );
    }

    @Test
    @DisplayName("Verify adding 10 different trips to cart")
    void add10DifferentTripsToCartTest() {
        WebElement categoriesElement = driver.findElement(By.cssSelector("div.columns-3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoriesElement);
        WebElement firstCategory = driver.findElement(By.xpath("//li[@class='product-category product first']"));
        firstCategory.click();

        int productsAddedToCart = 0;

        productsAddedToCart = addAllProductsToCart(productsAddedToCart);

        driver.findElement(By.cssSelector(".cat-item-19 a")).click();

        productsAddedToCart = addAllProductsToCart(productsAddedToCart);

        driver.findElement(By.cssSelector(".cart-contents")).click();

        List<WebElement> productsInCart = driver.findElements(By.cssSelector(".cart_item"));
        Assertions.assertEquals(productsAddedToCart, productsInCart.size(), "The number of items in cart is not correct");
    }

    private int addAllProductsToCart(int productsCounter) {
        List<WebElement> categoryProducts = driver.findElements(By.cssSelector(".type-product"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoryProducts.get(0));
        for (WebElement product: categoryProducts) {
            product.findElement(By.cssSelector(".button")).click();
            By loadingWheel = By.cssSelector(".loading");
            wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheel, 0));
            productsCounter++;
        }
        return productsCounter;
    }
}
