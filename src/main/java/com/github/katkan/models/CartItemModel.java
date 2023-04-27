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
    private String productLink;
    private String name;
    private String price;
    private int quantity;
    private String subtotalPrice;
}
