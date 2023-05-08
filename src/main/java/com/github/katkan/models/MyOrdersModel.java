package com.github.katkan.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;

@Getter
@Setter
@Builder
public class MyOrdersModel {
    private String date;
    private String status;
    private String sum;
    private WebElement orderLink;
    private WebElement viewOrder;
}
