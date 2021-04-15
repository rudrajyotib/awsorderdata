package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PersistenceRepository orderRepository;

    @Override
    public Order searchOrder(String orderNumber) {
        OrderEntity orderEntity = orderRepository.findOrderByOrderNumber(orderNumber);
        if (orderEntity == null) {
            return null;
        }
        return new Order(orderEntity.getOrderNumber(), orderEntity.getProduct());
    }

    @Override
    public void addOrder(Order order) {
        orderRepository.addOrder(new OrderEntity(order.getOrderNumber(),
                order.getProduct()));
    }
}