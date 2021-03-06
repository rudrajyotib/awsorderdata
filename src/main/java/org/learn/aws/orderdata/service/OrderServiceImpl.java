package org.learn.aws.orderdata.service;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PersistenceRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public Order searchOrder(String orderNumber) {
        OrderEntity orderEntity = orderRepository.findOrderByOrderNumber(orderNumber);
        if (orderEntity == null) {
            return null;
        }
        return new Order(orderEntity.getOrderNumber(), orderEntity.getProduct());
    }

    @Override
    public int addOrder(Order order) {
        try {
            orderRepository.addOrder(new OrderEntity(order.getOrderNumber(),
                    order.getProduct()));
            return 0;
        } catch (DataIntegrityViolationException duplicateKeyException) {
            return 1;
        } catch (Exception exception) {
            return 2;
        }
    }

    @Override
    public int updateOrder(Order order) {
        try {
            transactionTemplate.executeWithoutResult(transactionStatus -> {
                OrderEntity orderFromRepository = orderRepository.findOrderByOrderNumber(order.getOrderNumber());
                if (orderFromRepository != null) {
                    orderFromRepository.setProduct(order.getProduct());
                    orderRepository.updateOrder(orderFromRepository);
                }
            });
            return 0;
        } catch (Exception exception) {
            return 2;
        }
    }
}
