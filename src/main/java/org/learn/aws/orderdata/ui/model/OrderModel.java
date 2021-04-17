package org.learn.aws.orderdata.ui.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderModel {

    @Size(min = 3, max = 8)
    @NotNull
    private final String orderNumber;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    private final String product;


    public OrderModel(String orderNumber, String product) {
        this.orderNumber = orderNumber;
        this.product = product;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProduct() {
        return product;
    }


}
