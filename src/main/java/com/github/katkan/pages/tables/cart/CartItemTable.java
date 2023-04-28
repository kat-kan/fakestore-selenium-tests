package com.github.katkan.pages.tables.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
                .forEach(r-> cartItems.add(new CartItemRow(driver).toModel()));
    }

    public CartItemModel getCartItem(String name){
        return cartItems.stream()
                .filter(cartItem-> cartItem.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product " + name + " not found"));
    }
}
