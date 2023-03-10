package com.github.katkan.tests.checkout;

import com.github.katkan.pageObjects.*;
import com.github.katkan.properties.Properties;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTests extends BaseTest {

    static final String PAYMENT_METHOD = "Karta debetowa/kredytowa (Stripe)";

    String productUrl = "https://fakestore.testelka.pl/product/fuerteventura-sotavento/";
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
    void prepareOrder() {
        ProductPage productPage = new ProductPage(driver);
        productPage.goTo(productUrl)
                .footer
                .closeCookieConsentBar();
        productPage.addToCart()
                .viewCart();
    }

    @Test
    @DisplayName("Create an order without creating new account")
    void orderWithoutCreatingNewAccountTest() {
        CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

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

        assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                "Order confirmation is not displayed");
    }

    @Test
    @DisplayName("Login to existing account during order process. Create an order and check order summary correctness")
    void orderAfterLoggingInOnExistingAccountTest() {
        String totalPrice = new CartPage(driver).getTotalPrice();
        CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

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
                () -> assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                        "Order confirmation is not displayed"),
                () -> assertEquals(orderReceivedPage.getCurrentDateInSpecifiedFormat(), orderReceivedPage.getOrderDate(),
                        "The date of the order is not correct or correctly formatted" + orderReceivedPage.getCurrentDateInSpecifiedFormat()),
                () -> assertNotNull(orderReceivedPage.getOrderId(), "Order number was not generated"),
                () -> assertEquals(totalPrice, orderReceivedPage.getOrderTotalPrice(),
                        "Total price is different than given in the cart : " + totalPrice),
                () -> assertEquals(PAYMENT_METHOD, orderReceivedPage.getPaymentMethod(),
                        "Payment method is not correct, should be " + PAYMENT_METHOD)
                //TODO think about product-quantity assertion
        );
    }

    @Nested
    class CheckoutWithNewAccountCreationTests {

        @BeforeEach
        void goToCheckout() {
            new CartPage(driver)
                    .goToCheckout();
        }

        @Test
        @DisplayName("Check that user can create an account during order process and finish the order")
        void orderAfterCreatingANewAccount() {
            OrderReceivedPage orderReceivedPage = new CheckoutPage(driver).fillFirstNameField(firstName)
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

            assertDoesNotThrow(orderReceivedPage::isOrderSuccessfullyFinished,
                    "Order confirmation is not displayed");
        }


        @Test
        @DisplayName("Create an order and check it in My Account page")
        void checkOrdersInMyAccountPageTest() {
            OrderReceivedPage orderReceivedPage = new CheckoutPage(driver).fillFirstNameField(firstName)
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

            String orderId = orderReceivedPage.getOrderId();
            String orderDate = orderReceivedPage.getOrderDate();

            OrderDetailsPage orderDetailsPage = orderReceivedPage.header.viewMyAccount()
                    .viewOrders()
                    .goToOrderDetails(orderId);

            Assertions.assertAll(
                    () -> assertEquals(orderId, orderDetailsPage.getOrderId(),
                            "The id on order details page should be " + orderId),
                    () -> assertEquals(orderDate, orderDetailsPage.getOrderDate(),
                            "The order date on order details page should be " + orderDate)
            );

        }

        @AfterEach
        void deleteAccount() {
            new OrderReceivedPage(driver)
                    .header
                    .viewMyAccount()
                    .deleteAccount();
        }
    }

    @Nested
    class CheckoutDataValidationTests {

        @Test
        @DisplayName("Check empty first name validation message")
        void checkFirstNameFieldValidationTest() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillLastNameField(lastName)
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

            assertEquals("Imię płatnika jest wymaganym polem", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check empty last name validation message")
        void checkLastNameFieldValidationTest() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
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

            assertEquals("Nazwisko płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check empty address validation message")
        void checkAddressFieldValidationTest() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillPostcodeField(postcode)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillPhoneField(phone)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Ulica płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check empty postcode validation message")
        void checkPostcodeFieldValidationTest() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillPhoneField(phone)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Ulica płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());
        }
    }

}