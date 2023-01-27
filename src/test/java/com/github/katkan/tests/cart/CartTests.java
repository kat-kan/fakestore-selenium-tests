package com.github.katkan.tests.cart;

import com.github.katkan.pageObjects.CartPage;
import com.github.katkan.pageObjects.CategoryPage;
import com.github.katkan.pageObjects.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartTests extends BaseTest {

    WebDriverWait wait;

    By quantityField = By.cssSelector(".quantity input");
    By productLinkInCart = By.cssSelector("td.product-name a");
    By addToCartButton = By.cssSelector(".single_add_to_cart_button");
    By loadingWheel = By.cssSelector(".blockUI");

    @Test
    @DisplayName("Verify adding product to cart from the product page")
    void addProductToCartFromProductPageTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart().viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, cartPage.getProductQuantity(), "The quantity of product is not 1"),
                () -> Assertions.assertEquals(productUrl, cartPage.getProductLink(),
                        "The link in the cart is not the link for Fuerteventura trip")
        );
    }

    @Test
    @DisplayName("Verify adding product to cart from category page")
    void addProductToCartFromCategoryPageTest() {
        String url = "https://fakestore.testelka.pl/product-category/yoga-i-pilates/";
        String productId = "61";

        CategoryPage categoryPage = new CategoryPage(driver).goTo(url);
        categoryPage.closeCookieConsentBar();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();

        Assertions.assertAll(
                () -> Assertions.assertTrue(cartPage.isProductDisplayed(productId),
                        "The product that was added is not displayed in the cart"),
                () -> Assertions.assertEquals(1, cartPage.getProductQuantity(),
                        "The quantity of product is not 1")
        );
    }

    @Test
    @DisplayName("Verify adding one product to cart ten times")
    void addOneProductToCart10TimesTest() {
        String productUrl = "https://fakestore.testelka.pl/product/wakacje-z-yoga-w-kraju-kwitnacej-wisni/";

        ProductPage productPage = new ProductPage(driver);
        for (int i = 0; i < 10; i++) {
            productPage.goTo(productUrl).closeCookieConsentBar();
            productPage.addToCart();
        }
        CartPage cartPage = productPage.viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals(10, cartPage.getProductQuantity(),
                        "The quantity of product is not 10"),
                () -> Assertions.assertEquals(productUrl, cartPage.getProductLink(),
                        "The link in the cart is not the link for Yoga in Japan trip")
        );
    }

    @Test
    @DisplayName("Verify adding product to cart from product page multiple times")
    void addProductToCartAndModifyAmountOnProductPageTest() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        int numberOfItems = 9;
        ProductPage productPage = new ProductPage(driver).goTo(url);
        productPage.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart(numberOfItems).viewCart();

        Assertions.assertAll(
                () -> Assertions.assertEquals(numberOfItems, cartPage.getProductQuantity(),
                        "The quantity of product is not " + numberOfItems),
                () -> Assertions.assertEquals(url,cartPage.getProductLink(),
                        "The link in the cart is not the link for Fuerteventura trip")
        );
    }

    @Test
    @DisplayName("Verify adding 10 different trips to cart")
    void addMoreThan10DifferentProductsToCartTest() {
        List<String> productsSpecificUrlParts = List.of("wakacje-z-yoga-w-kraju-kwitnacej-wisni/", "egipt-el-gouna/", "fuerteventura-sotavento/",
                "grecja-limnos/", "windsurfing-w-karpathos/", "windsurfing-w-lanzarote-costa-teguise/", "wyspy-zielonego-przyladka-sal/",
                "gran-koscielcow/", "wspinaczka-island-peak/", "wspinaczka-via-ferraty/");

        String productGenericUrlPart = "https://fakestore.testelka.pl/product/";
        productsSpecificUrlParts.forEach(x -> addProductToCart(productGenericUrlPart + x));
        viewCart();

        List<WebElement> productLinksInCart = driver.findElements(By.cssSelector(".cart_item .product-name a"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(productsSpecificUrlParts.size(), productLinksInCart.size(),
                        "The quantity of products is not " + productsSpecificUrlParts.size()),
                () -> {
                    for (WebElement productLinkElement : productLinksInCart) {
                        Assertions.assertTrue(productsSpecificUrlParts.contains(productLinkElement.getAttribute("href").replace(productGenericUrlPart, "")),
                                "The link in the cart is unknown");
                    }
                }
        );
    }

    @Test
    @DisplayName("Verify changing product amount on the cart page")
    void changeProductAmountInCartTest() {
        String productUrl = "https://fakestore.testelka.pl/product/wspinaczka-island-peak/";
        String finalQuantity = "4";
        addProductToCart(productUrl);
        viewCart();
        changeQuantity(finalQuantity);

        driver.findElement(By.name("update_cart")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheel, 0));

        Assertions.assertAll(
                () -> Assertions.assertEquals(finalQuantity, driver.findElement(quantityField).getAttribute("value"),
                        "The quantity of product is not " + finalQuantity),
                () -> Assertions.assertEquals(productUrl, driver.findElement(productLinkInCart).getAttribute("href"),
                        "The link in the cart is not the link for Island Peak trip")
        );
    }

    @Test
    @DisplayName("Verify removing product on the cart page")
    void removeProductFromCartTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        addProductToCart(productUrl);
        viewCart();

        driver.findElement(By.cssSelector(".remove")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingWheel, 0));

        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart-empty")).isDisplayed(),
                "Empty cart message is not displayed");
    }

    private void addProductToCart(String url) {
        driver.navigate().to(url);
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
