package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;

public interface OrderService {

    Order searchOrder(String orderNumber);

    void addOrder(Order order);

}
