package org.learn.aws.orderdata.persistence.dummy;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.springframework.stereotype.Component;


@Component
public class FixedValuePersistenceRepository implements PersistenceRepository {
    @Override
    public Order searchOrder(String orderNumber) {
        return new Order(orderNumber, "AWS");
    }
}
