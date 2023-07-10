package com.github.katkan.pages.tables.my_orders;

import com.github.katkan.models.MyOrdersModel;
import com.github.katkan.pages.account.OrderDetailsPage;
import com.github.katkan.pages.main.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyOrdersTable extends BasePage {

    @FindBy(css = ".woocommerce-orders-table")
    private WebElement ordersTableContainer;

    @FindBy(css = ".woocommerce-orders-table__row")
    private List<WebElement> rows;

    private final List<MyOrdersModel> myOrdersList = new ArrayList<>();

    public MyOrdersTable(WebDriver driver) {
        super(driver);
        initializeAllOrders();
    }

    public OrderDetailsPage navigateToOrder(String id) {
        this.getOrder(id).getOrderLink().click();
        return new OrderDetailsPage(driver);
    }

    public MyOrdersModel getOrder(String id) {
        return myOrdersList.stream()
                .filter(myOrdersModel -> myOrdersModel.getOrderLink().getText().contains(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Order with id " + id + "not found"));
    }

    private void initializeAllOrders() {
        rows.stream()
                .parallel()
                .forEach(row -> myOrdersList.add(new MyOrdersRow(driver, row).toModel()));
    }
}
