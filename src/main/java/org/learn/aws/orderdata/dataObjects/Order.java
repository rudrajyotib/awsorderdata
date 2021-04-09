package org.learn.aws.orderdata.dataObjects;

public class Order {

    private final String orderNumber;
    private final String product;


    public Order(String orderNumber, String product) {
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
