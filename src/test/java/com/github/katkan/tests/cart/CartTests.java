package com.github.katkan.tests.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.category.CategoryPage;
import com.github.katkan.pages.product.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends BaseTest {

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

        softly.assertThat(productInCart.getQuantity())
                .as("The quantity of product is not 1")
                .isEqualTo(1);
        softly.assertThat(productInCart.getProductLink())
                .as("The link in the cart is not the link to product's page")
                .isEqualTo(productUrl);
    }

    @Test
    @DisplayName("Check adding product to cart from category page")
    void addProductToCartFromCategoryPage() {
        String url = "https://fakestore.testelka.pl/product-category/yoga-i-pilates/";
        String productId = "61";
        CategoryPage categoryPage = new CategoryPage(driver).goTo(url);
        categoryPage.footer.closeCookieConsentBar();

        CartPage cartPage = categoryPage.addToCart(productId).viewCart();

        //TODO AssertJ SoftAssertions do not support this method. Need to think of custom implementation
/*        softly.assertThatNoException()
                        .as("The product that was added is not displayed in the cart")
                        .isThrownBy(() -> cartPage.isProductDisplayed(productId));*/
        softly.assertThat(cartPage.getProductQuantity())
                .as("The quantity of product is not 1")
                .isEqualTo(1);

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

        softly.assertThat(cartPage.getProductQuantity())
                .as("The quantity of product is not 10")
                .isEqualTo(10);
        softly.assertThat(cartPage.getProductLink())
                .as("The link in the cart is not the link for Yoga in Japan trip")
                .isEqualTo(productUrl);
    }

    @Test
    @DisplayName("Check adding product to cart and modifying the amount on the product page")
    void addProductToCartAndModifyAmountOnProductPage() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        int numberOfItems = 9;
        ProductPage productPage = new ProductPage(driver).goTo(url);
        productPage.footer.closeCookieConsentBar();

        CartPage cartPage = productPage.addToCart(numberOfItems).viewCart();

        softly.assertThat(cartPage.getProductQuantity())
                .as("The quantity of product is not " + numberOfItems)
                .isEqualTo(numberOfItems);
        softly.assertThat(cartPage.getProductLink())
                .as("The link in the cart is not the link for Fuerteventura trip")
                .isEqualTo(url);
    }

    @Test
    @DisplayName("Check adding 10 different trips to cart")
    void add10DifferentProductsToCart() {
        List<String> productPages = new ArrayList<>(List.of("wakacje-z-yoga-w-kraju-kwitnacej-wisni/", "egipt-el-gouna/", "fuerteventura-sotavento/",
                "grecja-limnos/", "windsurfing-w-karpathos/", "windsurfing-w-lanzarote-costa-teguise/", "wyspy-zielonego-przyladka-sal/",
                "gran-koscielcow/", "wspinaczka-island-peak/", "wspinaczka-via-ferraty/"));

        ProductPage productPage = new ProductPage(driver);
        String productGenericUrlPart = "https://fakestore.testelka.pl/product/";

        productPages.forEach(product -> productPage.goTo(productGenericUrlPart + product).addToCart());
        CartPage cartPage = productPage.header.viewCart();
        List<String> cartItemsProductPages = cartPage.getCartItemsTable().getProductPagePartOfUrlsForAllCartItems();
        Collections.sort(productPages);

        softly.assertThat(cartPage.getNumberOfProducts())
                .as("The number of products is not " + productPages.size())
                .isEqualTo(productPages.size());
        softly.assertThatList(cartItemsProductPages).isEqualTo(productPages);
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

        softly.assertThat(cartPage.getProductQuantity())
                .as("The quantity of product is not " + quantity)
                .isEqualTo(quantity);
        softly.assertThat(cartPage.getProductLink())
                .as("The link in the cart is not the correct link " + url)
                .isEqualTo(url);
    }

    @Test
    @DisplayName("Check removing product on the cart page")
    void removeProductFromCart() {
        String url = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver);
        productPage.goTo(url).footer.closeCookieConsentBar();
        CartPage cartPage = productPage.addToCart().viewCart();

        cartPage.removeProduct();

        assertThat(cartPage.isCartEmpty())
                .as("Empty cart message is not displayed")
                .isTrue();
    }
}

