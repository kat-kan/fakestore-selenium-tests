package com.github.katkan.pages.tables.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartItemRow extends BasePage {

    @FindBy(css = ".product-remove")
    private WebElement removeButtonColumn;

    @FindBy(css = "product-thumbnail")
    private WebElement productThumbnailColumn;

    @FindBy(css = "product-name")
    private WebElement productLinkColumn;

    @FindBy(css = "product-price")
    private WebElement priceColumn;

    @FindBy(css = "product-quantity")
    private WebElement quantityColumn;

    @FindBy(css = "product-subtotal")
    private WebElement subtotalPriceColumn;


    public CartItemRow(WebDriver driver) {
        super(driver);
    }

    public CartItemModel toModel() {
        return CartItemModel.builder()
                .removeButton(removeButtonColumn)
                .thumbnail(productThumbnailColumn)
                .productLink(productLinkColumn)
                .price(priceColumn.getText())
                .quantity(quantityColumn.getText())
                .subtotalPrice(subtotalPriceColumn.getText())
                .build();
    }
}
