package org.learn.aws.orderdata.persistence;

import org.apache.commons.lang3.StringUtils;
import org.learn.aws.orderdata.persistence.entities.AuditEntity;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class OrderRepository implements PersistenceRepository {

    @Autowired
    private EntityManager entityManager;

    @Value("${system.host.name}")
    private String systemHostName;

    @Override
    public OrderEntity findOrderByOrderNumber(String orderNumber) {
        return entityManager.find(OrderEntity.class, orderNumber);
    }

    @Override
    public void addOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        entityManager.persist(new AuditEntity("ORDER",
                orderEntity.getOrderNumber(), "CREATE", StringUtils.isEmpty(systemHostName) ? "NOT AVAILABLE" : systemHostName));
    }

    @Override
    public List<AuditEntity> listAllAuditEvents() {
        TypedQuery<AuditEntity> listAllAudits = entityManager.createNamedQuery("listAllAudits", AuditEntity.class);
        return listAllAudits.getResultList();
    }

    @Override
    public List<AuditEntity> getAuditEntriesByEntity(String entityNumber) {
        TypedQuery<AuditEntity> query = entityManager.createQuery("select a from AuditEntity a where entityId = :entityNumber", AuditEntity.class);
        query.setParameter("entityNumber", entityNumber);
        return query.getResultList();
    }
}
