package com.github.katkan.tests.cart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
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
        By cookieConsentBar = By.cssSelector(".woocommerce-store-notice__dismiss-link");
        driver.findElement(cookieConsentBar).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Verify adding product to cart from the product page")
    void addProductToCartFromProductPageTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        addProductToCart(productUrl);
        viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals("1", driver.findElement(By.cssSelector(".quantity input")).getAttribute("value")),
                () -> Assertions.assertEquals(productUrl, driver.findElement(By.cssSelector("td.product-name a")).getAttribute("href"))
        );
    }

    @Test
    @DisplayName("Verify adding product to cart from category page")
    void addProductToCartFromCategoryPageTest() {
        driver.navigate().to("https://fakestore.testelka.pl/product-category/yoga-i-pilates/");
        By produvt = By.cssSelector("[data-product_id='61']");

        driver.findElement(produvt).click();
        By viewCartButton = By.cssSelector(".added_to_cart");
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
        driver.findElement(viewCartButton).click();

        Assertions.assertAll(
                () -> Assertions.assertTrue(driver.findElement(produvt).isDisplayed()),
                () -> Assertions.assertEquals("1", driver.findElement(By.cssSelector(".quantity input")).getAttribute("value"))
        );


    }

    @Test
    @DisplayName("Verify adding 10 trips to cart")
    void addOneProductToCart10TimesTest() {

        String yogaUrl = "https://fakestore.testelka.pl/product/wakacje-z-yoga-w-kraju-kwitnacej-wisni/";
        for (int i = 0; i < 10; i++) {
            addProductToCart(yogaUrl);
        }
        viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals("10", driver.findElement(By.cssSelector(".quantity input")).getAttribute("value")),
                () -> Assertions.assertEquals(yogaUrl, driver.findElement(By.cssSelector("td.product-name a")).getAttribute("href"))
        );
    }

    @Test
    @DisplayName("Verify adding multiple products to cart from product page")
    void addProductToCartAndModifyAmountOnProductPageTest() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        String numberOfItemsToAdd = "9";
        addProductToCart(url, numberOfItemsToAdd);
        viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals(numberOfItemsToAdd, driver.findElement(By.cssSelector(".quantity input")).getAttribute("value")),
                () -> Assertions.assertEquals(url, driver.findElement(By.cssSelector("td.product-name a")).getAttribute("href"))
        );
    }

    @Test
    @DisplayName("Verify adding minimum 10 different trips to cart")
    void addMoreThan10DifferentProductsToCartTest() {
        List<String> productsSpecificUrls = List.of("wakacje-z-yoga-w-kraju-kwitnacej-wisni/", "egipt-el-gouna/", "fuerteventura-sotavento/",
                "grecja-limnos/", "windsurfing-w-karpathos/", "windsurfing-w-lanzarote-costa-teguise/", "wyspy-zielonego-przyladka-sal/",
                "gran-koscielcow/", "wspinaczka-island-peak/", "wspinaczka-via-ferraty/");

        String productGenericUrl = "https://fakestore.testelka.pl/product/";
        productsSpecificUrls.forEach(x -> addProductToCart(productGenericUrl + x));
        viewCart();

        List<WebElement> elements = driver.findElements(By.cssSelector(".cart_item .product-name a"));
        elements.forEach(x -> x.getAttribute("href"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(productsSpecificUrls.size(), elements.size()),
                () -> {
                    for (WebElement element : elements) {
                        Assertions.assertTrue(productsSpecificUrls.contains(element.getAttribute("href").replace(productGenericUrl, "")));
                    }
                }
        );
    }

    @Test
    @DisplayName("Verify changing product amount on the cart page")
    void changeProductAmountInCartTest() {
        addProductToCart("https://fakestore.testelka.pl/product/wspinaczka-island-peak/");
        viewCart();
        changeQuantity("4");

        driver.findElement(By.name("update_cart")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".blockUI"), 0));

        Assertions.assertEquals("4", driver.findElement(By.cssSelector(".quantity input")).getAttribute("value"));
    }

    @Test
    @DisplayName("Verify removing product on the cart page")
    void removeProductFromCartTest() {
        addProductToCart("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
        viewCart();

        driver.findElement(By.cssSelector(".remove")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".blockUI"), 0));

        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart-empty")).isDisplayed());
    }

    private void addProductToCart(String url) {
        driver.navigate().to(url);
        By addToCartButton = By.cssSelector(".single_add_to_cart_button");
        driver.findElement(addToCartButton).click();
    }

    private void addProductToCart(String url, String amount) {
        driver.navigate().to(url);
        changeQuantity(amount);
        By addToCartButton = By.cssSelector(".single_add_to_cart_button");
        driver.findElement(addToCartButton).click();
    }

    private void viewCart() {
        By cartButton = By.cssSelector(".woocommerce-message .wc-forward");
        driver.findElement(cartButton).click();
    }

    private void changeQuantity(String amount) {
        WebElement quantityField = driver.findElement(By.cssSelector("[id^='quantity']"));
        quantityField.clear();
        quantityField.sendKeys(amount);
    }
}
