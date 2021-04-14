package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PersistenceRepository orderRepository;

    @Override
    public Order searchOrder(String orderNumber) {
        return orderRepository.searchOrder(orderNumber);
    }
}
