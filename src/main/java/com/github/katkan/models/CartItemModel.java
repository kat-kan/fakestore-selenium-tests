package com.github.katkan.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;

@Builder
@Getter
@Setter
public class CartItemModel {
    private WebElement removeButton;
    private WebElement thumbnail;
    private WebElement productLink;
    private String price;
    private String quantity;
    private String subtotalPrice;
}
