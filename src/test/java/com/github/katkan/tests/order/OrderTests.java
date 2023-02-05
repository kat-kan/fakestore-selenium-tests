package com.github.katkan.tests.order;

import com.github.katkan.pageObjects.OrderPage;
import com.github.katkan.pageObjects.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTests extends BaseTest {

    @Test
    @DisplayName("Check that order can be finished without creating new account")
    void orderProductWithoutNewAccountCreationTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver);
        OrderPage orderPage = productPage.goTo(productUrl).addToCart().viewCart().goToCheckout();
        orderPage.fillOrderData().fillPaymentData();
    }
}
