# fakestore-selenium-tests

Collection of Selenium tests for Fakestore with Page Object Model

![](https://img.shields.io/badge/Code-Java%2017-informational?style=flat&color=blueviolet)
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

So far, it has 6 Page Objects: ProductPage, CategoryPage, CartPage, BasePage, HeaderPage and FooterPage:
* the first three ones represent the whole pages with methods and locators
* BasePage is an abstract class and each PageObject class extends it
* HeaderPage and FooterPage represent compontents visible on each page in the store. 

### Test cases covered (so far)
* check that user can add product to cart from product page
* check that user can add product to cart from category page
* check that user can add 10 products to cart
* check that user can modify amount of product on the product page
* check that user can add 10 different products to cart
* check that user can modify amount of product on the cart page
* check that user can remove product on the cart page

## Installation

There are few ways to start using this repository:
1. Clone with Git using command line and this command: `git clone https://github.com/kat-kan/fakestore-selenium-tests`
2. Clone with Git using graphical user interface (for example Sourcetree)

### Running the tests

You can run tests:
- using "Run test" option in Intellij Idea IDE
- using `mvn clean test` command in terminal
