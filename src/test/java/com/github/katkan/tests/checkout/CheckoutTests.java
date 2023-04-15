package com.github.katkan.tests.checkout;

import com.github.katkan.pages.account.OrderDetailsPage;
import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.checkout.CheckoutPage;
import com.github.katkan.pages.order.OrderReceivedPage;
import com.github.katkan.pages.product.ProductPage;
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
    void orderWithoutCreatingNewAccount() {
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
    void orderAfterLoggingInOnExistingAccount() {
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
        void checkOrdersInMyAccountPage() {
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
                    .navigateToOrder(orderId);

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
        void checkFirstNameFieldValidation() {
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

            assertEquals("Imię płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check empty last name validation message")
        void checkLastNameFieldValidation() {
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
        void checkAddressFieldValidation() {
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
        void checkPostcodeFieldValidation() {
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

            assertEquals("Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check empty street validation message")
        void checkStreetFieldValidation() {
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
        @DisplayName("Check empty email validation message")
        void checkEmailFieldValidation() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillPostcodeField(postcode)
                    .fillCityField(city)
                    .fillPhoneField(phone)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Adres email płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());

        }

        @Test
        @DisplayName("Check empty phone validation message")
        void checkPhoneFieldValidation() {
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillPostcodeField(postcode)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Telefon płatnika jest wymaganym polem.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check incorrect postcode (other than digits) validation message")
        void checkIncorrectPostcodeFieldValidation() {
            String incorrectPostcode = "not a postcode";
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillPostcodeField(incorrectPostcode)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillPhoneField(phone)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.", checkoutPage.getErrorMessageText());
        }

        @Test
        @DisplayName("Check incorrect phone (other than digits) validation message")
        void checkIncorrectPhoneFieldValidation() {
            String incorrectPhone = "not a phone number";
            CheckoutPage checkoutPage = new CartPage(driver).goToCheckout();

            checkoutPage.fillFirstNameField(firstName)
                    .fillLastNameField(lastName)
                    .fillCountryField(country)
                    .fillAddressField(address)
                    .fillPostcodeField(postcode)
                    .fillCityField(city)
                    .fillEmailField(email)
                    .fillPhoneField(incorrectPhone)
                    .fillCardNumberField(cardNumber)
                    .fillCardCvcField(cardCvc)
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertEquals("Telefon płatnika nie jest poprawnym numerem telefonu.", checkoutPage.getErrorMessageText());
        }
    }

}