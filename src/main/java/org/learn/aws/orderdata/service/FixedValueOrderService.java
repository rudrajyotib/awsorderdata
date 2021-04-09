package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;
import org.springframework.stereotype.Component;

@Component
public class FixedValueOrderService implements OrderService {
    @Override
    public Order searchOrder(String orderNumber) {
        return new Order(orderNumber, "AWS");
    }
}
