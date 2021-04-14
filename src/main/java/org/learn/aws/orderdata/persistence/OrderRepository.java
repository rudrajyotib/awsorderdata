package org.learn.aws.orderdata.persistence;

import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class OrderRepository implements PersistenceRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public OrderEntity findOrderByOrderNumber(String orderNumber) {
        return entityManager.find(OrderEntity.class, orderNumber);
    }

    @Override
    public void addOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
    }


}
