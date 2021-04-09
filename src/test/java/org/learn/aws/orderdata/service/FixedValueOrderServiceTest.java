package org.learn.aws.orderdata.service;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FixedValueOrderServiceTest {

    @Test
    public void shouldReturnFixedValueOrder() {
        OrderService fixedValueOrderService = new FixedValueOrderService();
        Order order = fixedValueOrderService.searchOrder("XYZ");
        assertNotNull(order);
        assertEquals(order.getOrderNumber(), "XYZ");
        assertEquals(order.getProduct(), "AWS");
    }

}