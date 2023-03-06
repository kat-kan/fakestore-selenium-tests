package com.github.katkan.tests.checkout;

import com.github.katkan.pageObjects.*;
import com.github.katkan.properties.Properties;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.*;

public class CheckoutTests extends BaseTest {

    static final String PAYMENT_METHOD = "Karta debetowa/kredytowa (Stripe)";

    ProductPage productPage;
    CartPage cartPage;

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
    String password = "Testowe_haslo_123";

    @BeforeEach
    void prepareOrder(){
        String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
        productPage = new ProductPage(driver);
        productPage.goTo(productUrl).footer.closeCookieConsentBar();
        cartPage = productPage.addToCart().viewCart();
    }

    @Test
    @DisplayName("Check that order can be finished without creating new account")
    void orderWithoutCreatingNewAccountTest() {
        CheckoutPage checkoutPage = cartPage.goToCheckout();

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

        Assertions.assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                "Order confirmation is not displayed");
    }

    @Test
    @DisplayName("Check that user can login to existing account during order process and finish the order. Check order summary correctness")
    void orderAfterLoggingInOnExistingAccountTest() {
        String totalPrice = cartPage.getTotalPrice();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        checkoutPage.login(Properties.getUsername(), Properties.getPassword());
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

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                        "Order confirmation is not displayed"),
                () -> Assertions.assertEquals(orderReceivedPage.getCurrentDateInSpecifiedFormat(), orderReceivedPage.getOrderDate(),
                        "The date of the order is not " + orderReceivedPage.getCurrentDateInSpecifiedFormat()),
                () -> Assertions.assertNotNull(orderReceivedPage.getOrderNumber(), "Order number was not generated"),
                () -> Assertions.assertEquals(totalPrice, orderReceivedPage.getOrderTotalPrice(),
                        "Total price is different than given in the cart : " + totalPrice),
                () -> Assertions.assertEquals(PAYMENT_METHOD, orderReceivedPage.getPaymentMethod(),
                        "Payment method is not correct, should be " + PAYMENT_METHOD)
                //TODO think about product-quantity assertion
        );
    }

    @Nested
    class CheckoutWithNewAccountCreationTests{

        OrderReceivedPage orderReceivedPage;

        @Test
        @DisplayName("Check that user can create an account during order process and finish the order")
        void orderAfterCreatingANewAccount() {
            CheckoutPage checkoutPage = cartPage.goToCheckout();

            orderReceivedPage = checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillPostcodeField(postcode)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillPhoneField(phone)
                    .createNewAccount(password)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            Assertions.assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                    "Order confirmation is not displayed");
        }

        @AfterEach
        void deleteAccount(){
            AccountPage accountPage = new OrderReceivedPage(driver).header.viewMyAccount();
            accountPage.deleteAccount();
        }
    }

}