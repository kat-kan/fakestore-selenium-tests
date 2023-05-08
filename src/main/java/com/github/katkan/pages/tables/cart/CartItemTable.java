package com.github.katkan.pages.tables.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CartItemTable extends BasePage {

    @FindBy(css = ".woocommerce-cart-form__contents")
    private WebElement table;

    @FindBy(css = ".cart_item")
    private List<WebElement> rows;

    private final List<CartItemModel> cartItems = new ArrayList<>();

    public CartItemTable(WebDriver driver) {
        super(driver);
        initializeCartItems();
    }

    private void initializeCartItems(){
        rows.stream()
                .parallel()
                .forEach(r-> cartItems.add(new CartItemRow(driver, r).toModel()));
    }

    public CartItemModel getCartItem(String name){
        return cartItems.stream()
                .filter(cartItem-> cartItem.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product " + name + " not found"));
    }

    public List<String> getProductPagePartOfUrlsForAllCartItems(){
        String productGenericUrlPart = "https://fakestore.testelka.pl/product/";
        return cartItems.stream()
                .map(CartItemModel::getProductLink)
                .map(productLink -> productLink.replace(productGenericUrlPart, ""))
                .sorted()
                .collect(Collectors.toList());
    }
}
