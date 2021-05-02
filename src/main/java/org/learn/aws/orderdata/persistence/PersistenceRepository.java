package org.learn.aws.orderdata.persistence;

import org.learn.aws.orderdata.persistence.entities.AuditEntity;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;

import java.util.List;

public interface PersistenceRepository {

    OrderEntity findOrderByOrderNumber(String orderNumber);

    void addOrder(OrderEntity orderEntity);

    List<AuditEntity> listAllAuditEvents();

    List<AuditEntity> getAuditEntriesByEntity(String entityNumber);
}
