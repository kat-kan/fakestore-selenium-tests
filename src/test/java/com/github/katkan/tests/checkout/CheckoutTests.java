package com.github.katkan.tests.checkout;

import com.github.katkan.pageObjects.CheckoutPage;
import com.github.katkan.pageObjects.OrderReceivedPage;
import com.github.katkan.pageObjects.ProductPage;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTests extends BaseTest {

    String firstName = "Joanna";
    String lastName = "Testowa";
    String country = "Polska";
    String address = "ul. Wrocławska 25";
    String postcode = "00-001";
    String city = "Warsaw";
    String email = "testowajoanna@test.pl";
    String phone = "555-666-777";
    String cardNumber = "4242424242424242";
    String cardExpiryDate = "1030";
    String cardCvc = "123";

    @Test
    @DisplayName("Check that order can be finished without creating new account")
    void orderWithoutCreatingNewAccountTest() {
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        ProductPage productPage = new ProductPage(driver);
        productPage.goTo(productUrl).footer.closeCookieConsentBar();
        CheckoutPage checkoutPage = productPage.addToCart().viewCart().goToCheckout();
        //TODO scroll to button checout
        OrderReceivedPage orderReceivedPage = checkoutPage.fillFirstNameField(firstName)
                .fillLastNameField(lastName)
                .fillCountryField(country)
                .fillAddressField(address)
                .fillPostcodeField(postcode)
                .fillCityField(city)
                .fillEmailField(email)
                .fillPhoneField(phone)
                .fillCardNumberField(cardNumber)
                .fillCardCvcField(cardCvc)
                .fillCardExpiryDateField(cardExpiryDate)
                .acceptTermsAndConditions()
                .confirm();

        Assertions.assertTrue(orderReceivedPage.isOrderSuccessfullyFinished());
    }
}
