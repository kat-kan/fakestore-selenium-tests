# fakestore-selenium-tests

Collection of Selenium tests for Fakestore with Page Object Model

![](https://img.shields.io/badge/Code-Java%2019-informational?style=flat&color=blueviolet)
![](https://img.shields.io/badge/Framework-JUnit%205-informational?style=flat&&color=blueviolet)
![](https://img.shields.io/badge/Library-Selenium%20Webdriver-informational?style=flat&&color=blueviolet)
![](https://img.shields.io/badge/Tool-Maven-informational?style=flat&&color=blueviolet)

App links : [Fakestore main page](https://fakestore.testelka.pl/),  [Fakestore Docs](https://fakestore.testelka.pl/dokumentacja/)

*Fakestore* is a fake web store created by @Testelka for the purpose of test automation training. Fakestore imitates a real online shop with travels and provides:
- products' descriptions on product pages
- product's categories
- ability to add products to cart and finish the order
- ability to add products to wishlist
- user account creation

## Short test framework description

This test framework is being built with **Page Object Pattern**. 

So far, it has 11 Page Objects: ProductPage, CategoryPage, CartPage, BasePage, HeaderPage and FooterPage, CheckoutPage, OrderDetailsPage, OrderReceivedPage, MyOrdersPage, AccountPage
* the first three ones represent the whole pages with methods and locators
* BasePage is an abstract class and each PageObject class extends it
* HeaderPage and FooterPage represent compontents visible on each page in the store
* the last five were added during creation of CheckoutTests and also represent pages with methods and locators

### Test cases covered (so far)
* check that user can add product to cart from product page
* check that user can add product to cart from category page
* check that user can add 10 products to cart
* check that user can modify amount of product on the product page
* check that user can add 10 different products to cart
* check that user can modify amount of product on the cart page
* check that user can remove product on the cart page
* check that user can finish order without creating new account
* check that user can finish order after logging to existing account
* check that user can finish order with creating new account
* check that user can view order in "My Account" section
* check validations for all mandatory fields during checkout

## Installation

There are few ways to start using this repository:
1. Clone with Git using command line and this command: `git clone https://github.com/kat-kan/fakestore-selenium-tests`
2. Clone with Git using graphical user interface (for example Sourcetree)

### Running the tests

You can run tests:
- using "Run test" option in Intellij Idea IDE 	⚠️ in Java 19 we need to add VM options "-Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -Dfile.encoding=UTF-8" in run test config to see correct Polish letters
- using `mvn clean test` command in terminal
