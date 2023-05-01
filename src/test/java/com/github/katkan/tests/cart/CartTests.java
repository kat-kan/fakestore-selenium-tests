package com.github.katkan.tests.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.category.CategoryPage;
import com.github.katkan.pages.product.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CartTests extends BaseTest {

    List<String> productPages = new ArrayList<>(List.of("wakacje-z-yoga-w-kraju-kwitnacej-wisni/", "egipt-el-gouna/", "fuerteventura-sotavento/",
            "grecja-limnos/", "windsurfing-w-karpathos/", "windsurfing-w-lanzarote-costa-teguise/", "wyspy-zielonego-przyladka-sal/",
            "gran-koscielcow/", "wspinaczka-island-peak/", "wspinaczka-via-ferraty/"));

    @Test
    @DisplayName("Check adding product to cart from the product page")
    void addProductToCartFromProductPage() {
        String productUrl = "https://fakestore.testelka.pl/product/windsurfing-w-karpathos/";
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        String productName = productPage.getProductName();
        productPage.footer.closeCookieConsentBar();
        CartItemModel productInCart = productPage.addToCart()
                .viewCart()
                .getCartItemsTable()
                .getCartItem(productName);

        Assertions.assertAll(
                () -> assertThat(productInCart.getQuantity())
                        .as("The quantity of product is not 1")
                        .isEqualTo(1),
                ()-> assertThat(productInCart.getProductLink())
                        .as("The link in the cart is not the link to product's page")
                        .isEqualTo(productUrl)
        );
    }

    @Test
    @DisplayName("Check adding product to cart from category page")
    void addProductToCartFromCategoryPage() {
        String url = "https://fakestore.testelka.pl/product-category/yoga-i-pilates/";
        String productId = "61";

        CategoryPage categoryPage = new CategoryPage(driver).goTo(url);
        categoryPage.footer.closeCookieConsentBar();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();

        Assertions.assertAll(
                () -> assertThatNoException()
                        .as("The product that was added is not displayed in the cart")
                        .isThrownBy(() -> cartPage.isProductDisplayed(productId)),
                () -> assertThat(cartPage.getProductQuantity())
                        .as("The quantity of product is not 1")
                        .isEqualTo(1)
        );
    }

    @Test
    @DisplayName("Check adding one product to cart ten times")
    void addOneProductToCart10Times() {
        String productUrl = "https://fakestore.testelka.pl/product/wakacje-z-yoga-w-kraju-kwitnacej-wisni/";

        ProductPage productPage = new ProductPage(driver);
        for (int i = 0; i < 10; i++) {
            productPage.goTo(productUrl).addToCart();
        }

        CartPage cartPage = productPage.viewCart();

        Assertions.assertAll(
                () -> assertThat(cartPage.getProductQuantity())
                        .as("The quantity of product is not 10")
                        .isEqualTo(10),
                () -> assertThat(cartPage.getProductLink())
                        .as("The link in the cart is not the link for Yoga in Japan trip")
                        .isEqualTo(productUrl)
        );
    }

    @Test
    @DisplayName("Check adding product to cart and modifying the amount on the product page")
    void addProductToCartAndModifyAmountOnProductPage() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        int numberOfItems = 9;
        ProductPage productPage = new ProductPage(driver).goTo(url);
        productPage.footer.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart(numberOfItems).viewCart();

        Assertions.assertAll(
                () -> assertThat(cartPage.getProductQuantity())
                        .as("The quantity of product is not " + numberOfItems)
                        .isEqualTo(numberOfItems),
                () -> assertThat(cartPage.getProductLink())
                        .as("The link in the cart is not the link for Fuerteventura trip")
                        .isEqualTo(url)
        );
    }

    @Test
    @DisplayName("Check adding 10 different trips to cart")
    void add10DifferentProductsToCart() {
        ProductPage productPage = new ProductPage(driver);
        String productGenericUrlPart = "https://fakestore.testelka.pl/product/";
        productPages.forEach(product -> productPage.goTo(productGenericUrlPart + product).addToCart());
        CartPage cartPage = productPage.header.viewCart();

//        List<WebElement> allProductsLinks = cartPage.getAllProductsLinks();

        List<String> dadada = cartPage.getCartItemsTable().getProductPagePartOfUrlsForAllCartItems();

        Assertions.assertAll(
                () -> Assertions.assertEquals(productPages.size(), cartPage.getNumberOfProducts(),
                        "The number of products is not " + productPages.size())
//                () -> allProductsLinks.stream()
//                        .map(productLinkElement -> productLinkElement.getAttribute("href"))
//                        .map(productLink -> productLink.replace(productGenericUrlPart, ""))
//                        .forEach(productLinkElement -> Assertions.assertTrue(productPages.contains(productLinkElement),
//                                "List of products that were added does not contain " + productLinkElement))
        );

        Collections.sort(productPages);

        Assertions.assertAll(
                () -> assertThat(cartPage.getNumberOfProducts())
                        .as("The number of products is not " + productPages.size())
                        .isEqualTo(productPages.size()),
                () -> assertThatList(dadada).isEqualTo(productPages)
        );
    }

    @Test
    @DisplayName("Check changing product amount on the cart page")
    void changeProductAmountInCart() {
        String url = "https://fakestore.testelka.pl/product/wspinaczka-island-peak/";
        int quantity = 4;
        ProductPage productPage = new ProductPage(driver);
        productPage.goTo(url).footer.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart().viewCart();
        cartPage.changeQuantity(quantity);

        Assertions.assertAll(
                () -> Assertions.assertEquals(quantity, cartPage.getProductQuantity(),
                        "The quantity of product is not " + quantity),
                () -> Assertions.assertEquals(url, cartPage.getProductLink(),
                        "The link in the cart is not the link for Island Peak trip")
        );
    }

    @Test
    @DisplayName("Check removing product on the cart page")
    void removeProductFromCart() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver);
        productPage.goTo(url).footer.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart().viewCart();
        cartPage.removeProduct();

        Assertions.assertTrue(cartPage.isCartEmpty(),
                "Empty cart message is not displayed");
    }
}
