package com.github.katkan.pages.category;

import com.github.katkan.pages.cart.CartPage;
import com.github.katkan.pages.main.BasePage;
import com.github.katkan.pages.main.FooterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;

public class CategoryPage extends BasePage {

    public FooterPage footer;

    @FindBy(css = ".added_to_cart")
    private WebElement viewCartButton;

    @FindBy(css = "[data-product_id]")
    private List<WebElement> categoryProducts;

    public CategoryPage(WebDriver driver) {
        super(driver);
        footer = new FooterPage(driver);
    }

    public CategoryPage goTo(String url) {
        driver.navigate().to(url);
        return this;
    }

    public CategoryPage addToCart(String id) {
        getProductById(id).click();
        return this;
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
        viewCartButton.click();
        return new CartPage(driver);
    }

    private WebElement getProductById(String id) {
        return categoryProducts.stream()
                .filter(e -> e.getAttribute("data-product_id").equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + "not found"));
    }
}
