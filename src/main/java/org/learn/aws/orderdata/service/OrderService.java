package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;

public interface OrderService {

    Order searchOrder(String orderNumber);

    int addOrder(Order order);

    int updateOrder(Order order);

}
