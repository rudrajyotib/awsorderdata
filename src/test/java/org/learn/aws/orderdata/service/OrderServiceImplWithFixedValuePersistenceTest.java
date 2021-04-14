package org.learn.aws.orderdata.service;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = OrderServiceImpl.class)
class OrderServiceImplWithFixedValuePersistenceTest {

    @MockBean
    private PersistenceRepository persistenceRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldReturnFixedValueOrder() {
        //OrderService fixedValueOrderService = new OrderServiceImpl();
        when(persistenceRepository.searchOrder(eq("XYZ"))).
                thenReturn(new Order("XYZ", "AWS"));

        Order order = orderService.searchOrder("XYZ");
        assertNotNull(order);
        assertEquals(order.getOrderNumber(), "XYZ");
        assertEquals(order.getProduct(), "AWS");
    }

}