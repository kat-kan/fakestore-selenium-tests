package com.github.katkan.tests.checkout;

import com.github.katkan.pageObjects.CheckoutPage;
import com.github.katkan.pageObjects.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTests extends BaseTest {

    @Test
    @DisplayName("Check that order can be finished without creating new account")
    void orderProductWithoutNewAccountCreationTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver);
        CheckoutPage checkoutPage = productPage.goTo(productUrl).addToCart().viewCart().goToCheckout();
        checkoutPage.fillOrderData().fillPaymentData().acceptTermsAndConditions().confirm();
    }
}
