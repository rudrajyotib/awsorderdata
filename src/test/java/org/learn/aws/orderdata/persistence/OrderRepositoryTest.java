package org.learn.aws.orderdata.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.learn.aws.orderdata.persistence.entities.AuditEntity;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = OrderRepository.class)
@AutoConfigurationPackage(basePackages = "org.learn.aws.orderdata.persistence")
@TestPropertySource(locations = "/org/learn/aws/orderdata/persistence/spring-h2-application-context-test.properties")
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@SuppressWarnings("NullableProblems") TransactionStatus transactionStatus) {

                orderRepository.addOrder(new OrderEntity("A111", "AWS"));

            }
        });
    }

    @Test
    public void shouldRetrieveOrder() {
        OrderEntity order = transactionTemplate.execute(transactionStatus -> orderRepository.findOrderByOrderNumber("A111"));
        List<AuditEntity> auditEvents = transactionTemplate.execute(transactionStatus -> orderRepository.listAllAuditEvents());
        assertNotNull(order);
        assertEquals("AWS", order.getProduct());
        assertNotNull(auditEvents);
        assertEquals(1, auditEvents.size());
        List<AuditEntity> queriedAuditEvents = transactionTemplate.execute(transactionStatus -> orderRepository.getAuditEntriesByEntity("A111"));
        assert queriedAuditEvents != null;
        assertEquals(1, queriedAuditEvents.size());
        AuditEntity auditEntity = queriedAuditEvents.get(0);
        assertEquals("A111", auditEntity.getEntityId());
        assertEquals("ORDER", auditEntity.getEntityType());
        assertEquals("CREATE", auditEntity.getEvent());
        assertEquals("host1", auditEntity.getHostName());
        assertNotNull(auditEntity.getCreateDate());
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            OrderEntity order1 = orderRepository.findOrderByOrderNumber("A111");
            order1.setProduct("UpdatedProduct");
            orderRepository.updateOrder(order1);
        });
        OrderEntity updatedOrder = transactionTemplate.execute(transactionStatus -> orderRepository.findOrderByOrderNumber("A111"));
        assertNotNull(updatedOrder);
        assertEquals("UpdatedProduct", updatedOrder.getProduct());
        List<AuditEntity> updatedAuditEvents = transactionTemplate.execute(transactionStatus -> orderRepository.getAuditEntriesByEntity("A111"));
        assertNotNull(updatedAuditEvents);
        assertEquals(2, updatedAuditEvents.size());

    }

    @Test()
    public void shouldThrowExceptionOnDuplicateInsertion() {
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> transactionTemplate
                        .execute((TransactionCallback<Void>) transactionStatus -> {
                            orderRepository.addOrder(new OrderEntity("A111", "AWS"));
                            return null;
                        }));
    }


}