package org.learn.aws.orderdata.persistence;

import org.learn.aws.orderdata.dataObjects.Order;

public interface PersistenceRepository {

    Order searchOrder(String orderNumber);

}
