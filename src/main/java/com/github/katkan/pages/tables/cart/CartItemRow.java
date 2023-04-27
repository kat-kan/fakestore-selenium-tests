package com.github.katkan.pages.tables.cart;

import com.github.katkan.models.CartItemModel;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartItemRow extends BasePage {

    @FindBy(css = ".product-remove>a")
    private WebElement removeButtonColumn;

    @FindBy(css = ".product-thumbnail>a")
    private WebElement productThumbnailColumn;

    @FindBy(css = ".product-name>a")
    private WebElement productLinkColumn;

    @FindBy(css = ".product-price bdi")
    private WebElement priceColumn;

    @FindBy(css = "[id^='quantity']")
    private WebElement quantityColumn;

    @FindBy(css = ".product-subtotal bdi")
    private WebElement subtotalPriceColumn;


    public CartItemRow(WebDriver driver) {
        super(driver);
    }

    public CartItemModel toModel() {
        return CartItemModel.builder()
                .removeButton(removeButtonColumn)
                .thumbnail(productThumbnailColumn)
                .productLink(productLinkColumn.getAttribute("href"))
                .name(productLinkColumn.getText())
                .price(priceColumn.getText())
                .quantity(Integer.parseInt(quantityColumn.getAttribute("value")))
                .subtotalPrice(subtotalPriceColumn.getText())
                .build();
    }
}
