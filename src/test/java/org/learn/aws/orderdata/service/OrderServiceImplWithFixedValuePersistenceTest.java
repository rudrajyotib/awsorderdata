package org.learn.aws.orderdata.service;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = OrderServiceImpl.class)
class OrderServiceImplWithFixedValuePersistenceTest {

    @MockBean
    private PersistenceRepository persistenceRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldReturnAnOrder() {
        //OrderService fixedValueOrderService = new OrderServiceImpl();
        when(persistenceRepository.findOrderByOrderNumber(eq("XYZ"))).
                thenReturn(new OrderEntity());

        Order order = orderService.searchOrder("XYZ");
        assertNotNull(order);
    }

    @Test
    public void shouldHandleOrderNotFoundInRepository() {
        //OrderService fixedValueOrderService = new OrderServiceImpl();
        when(persistenceRepository.findOrderByOrderNumber(eq("XYZ"))).
                thenReturn(null);

        Order order = orderService.searchOrder("XYZ");
        assertNull(order);
    }

    @Test
    public void shouldAddOrder() {
        doNothing().when(persistenceRepository).addOrder(argThat(orderEntity -> {
            assertNotNull(orderEntity);
            assertEquals("AA", orderEntity.getOrderNumber());
            assertEquals("AA", orderEntity.getProduct());
            return true;
        }));

        orderService.addOrder(new Order("AA", "AA"));
    }

}