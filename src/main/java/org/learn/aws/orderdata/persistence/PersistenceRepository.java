package org.learn.aws.orderdata.persistence;

import org.learn.aws.orderdata.persistence.entities.OrderEntity;

public interface PersistenceRepository {

    OrderEntity findOrderByOrderNumber(String orderNumber);

    void addOrder(OrderEntity orderEntity);

}
