package com.github.katkan.pages.tables.my_orders;

import com.github.katkan.models.MyOrdersModel;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyOrdersRow extends BasePage {

    @FindBy(css = "[class$='cell-order-number']>a")
    private WebElement orderLinkColumn;

    @FindBy(css = "[class$='cell-order-date']")
    private WebElement dateColumn;

    @FindBy(css = "[class$='cell-order-status']")
    private WebElement statusColumn;

    @FindBy(css = "[class$='cell-order-total']")
    private WebElement sumColumn;

    @FindBy(css = "[class$='cell-order-actions']")
    private WebElement viewOrderColumn;

    public MyOrdersRow(WebDriver driver, WebElement row) {
        super(driver);
//        PageFactory.initElements(new DefaultElementLocatorFactory(rowContext), this);
    }

    public MyOrdersModel toModel() {
        return MyOrdersModel.builder()
                .orderLink(orderLinkColumn)
                .date(dateColumn.getText())
                .status(statusColumn.getText())
                .sum(sumColumn.getText())
                .viewOrder(viewOrderColumn)
                .build();
    }
}
