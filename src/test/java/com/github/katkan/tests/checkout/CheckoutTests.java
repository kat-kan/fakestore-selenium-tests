package com.github.katkan.tests.checkout;

import com.github.katkan.enums.PaymentMethod;
import com.github.katkan.helpers.DateHelper;
import com.github.katkan.pages.account.OrderDetailsPage;
import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.checkout.CheckoutPage;
import com.github.katkan.pages.order.OrderReceivedPage;
import com.github.katkan.pages.product.ProductPage;
import com.github.katkan.properties.Properties;
import com.github.katkan.tests.base.BaseTest;
import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class CheckoutTests extends BaseTest {

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
        productPage.goTo(productUrl).footer.closeCookieConsentBar();
        productPage.addToCart().viewCart();
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
                .cardPage
                .fillCardNumberField(cardNumber)
                .cardPage
                .fillCardCvcField(cardCvc)
                .cardPage
                .fillCardExpiryDateField(cardExpiryDate)
                .acceptTermsAndConditions()
                .confirm();

        assertThatNoException()
                .as("Order confirmation is not displayed")
                .isThrownBy(orderReceivedPage::isOrderSuccessfullyFinished);
    }

    @Test
    @DisplayName("Login to existing account during order process. Create an order and check order summary correctness")
    void orderAfterLoggingInOnExistingAccount() {
        String totalPrice = new CartPage(driver).getTotalPrice();
        SimpleDateFormat orderSummaryDateFormat = new SimpleDateFormat("d MMMM, yyyy", Locale.forLanguageTag("pl"));
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
                .cardPage
                .fillCardNumberField(cardNumber)
                .cardPage
                .fillCardCvcField(cardCvc)
                .cardPage
                .fillCardExpiryDateField(cardExpiryDate)
                .acceptTermsAndConditions()
                .confirm();

        softly.assertThat(orderReceivedPage.getDate())
                .as("The date of the order is not correct or correctly formatted" + DateHelper.getCurrentDateInSpecifiedFormat(orderSummaryDateFormat))
                .isEqualTo(DateHelper.getCurrentDateInSpecifiedFormat(orderSummaryDateFormat));
        softly.assertThat(orderReceivedPage.getId())
                .as("Order number was not generated")
                .isNotNull();
        softly.assertThat(orderReceivedPage.getTotalPrice())
                .as("Total price is different than given in the cart : " + totalPrice)
                .isEqualTo(totalPrice);
        softly.assertThat(orderReceivedPage.getPaymentMethod())
                .as("Payment method is not correct, should be " + PaymentMethod.CARD.getDescription())
                .isEqualTo(PaymentMethod.CARD.getDescription());
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThatNoException()
                    .as("Order confirmation is not displayed")
                    .isThrownBy(orderReceivedPage::isOrderSuccessfullyFinished);
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            String orderId = orderReceivedPage.getId();
            String orderDate = orderReceivedPage.getDate();

            OrderDetailsPage orderDetailsPage = orderReceivedPage.header.viewMyAccount()
                    .viewOrders()
                    .navigateToOrder(orderId);

            softly.assertThat(orderDetailsPage.getId())
                    .as("The id on order details page should be " + orderId)
                    .isEqualTo(orderId);
            softly.assertThat(orderDetailsPage.getDate())
                    .as("The order date on order details page should be " + orderDate)
                    .isEqualTo(orderDate);
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Imię płatnika jest wymaganym polem.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Nazwisko płatnika jest wymaganym polem.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Ulica płatnika jest wymaganym polem.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Ulica płatnika jest wymaganym polem.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Adres email płatnika jest wymaganym polem.");

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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Telefon płatnika jest wymaganym polem.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.");
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
                    .cardPage
                    .fillCardNumberField(cardNumber)
                    .cardPage
                    .fillCardCvcField(cardCvc)
                    .cardPage
                    .fillCardExpiryDateField(cardExpiryDate)
                    .acceptTermsAndConditions()
                    .confirm();

            assertThat(checkoutPage.getErrorMessageText())
                    .isEqualTo("Telefon płatnika nie jest poprawnym numerem telefonu.");
        }
    }

}