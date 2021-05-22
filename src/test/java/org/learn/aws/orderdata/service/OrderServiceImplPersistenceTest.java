package org.learn.aws.orderdata.service;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.persistence.PersistenceRepository;
import org.learn.aws.orderdata.persistence.entities.OrderEntity;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = OrderServiceImpl.class)
class OrderServiceImplPersistenceTest {

    @MockBean
    private PersistenceRepository persistenceRepository;

    @MockBean
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderService orderService;

    private TransactionStatus transactionStatus;


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

        int result = orderService.addOrder(new Order("AA", "AA"));

        assertEquals(0, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldUpdateOrder() {

        transactionStatus = mock(TransactionStatus.class);
        when(persistenceRepository.findOrderByOrderNumber("A111")).thenReturn(new OrderEntity("A111", "OriginalProduct"));
        doNothing().when(persistenceRepository).updateOrder(any(OrderEntity.class));
        doAnswer(invocationOnMock -> {
            Consumer<TransactionStatus> consumer = invocationOnMock.getArgument(0);
            consumer.accept(transactionStatus);
            return null;
        }).when(transactionTemplate).executeWithoutResult(isA(Consumer.class));
        int result = orderService.updateOrder(new Order("A111", "UpdatedOrder"));
        assertEquals(0, result);

        verify(persistenceRepository, times(1)).findOrderByOrderNumber("A111");
        ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(persistenceRepository, times(1)).updateOrder(orderEntityArgumentCaptor.capture());
        List<OrderEntity> allOrderEntityArgumentsForUpdateOrder = orderEntityArgumentCaptor.getAllValues();
        assertEquals(1, allOrderEntityArgumentsForUpdateOrder.size());
        assertEquals("UpdatedOrder", allOrderEntityArgumentsForUpdateOrder.get(0).getProduct());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldHandleExceptionInUpdateOrder() {

        transactionStatus = mock(TransactionStatus.class);
        when(persistenceRepository.findOrderByOrderNumber("A111")).thenReturn(new OrderEntity("A111", "OriginalProduct"));
        doThrow(new IllegalStateException("Mock exception")).when(persistenceRepository).updateOrder(any(OrderEntity.class));
        doAnswer(invocationOnMock -> {
            Consumer<TransactionStatus> consumer = invocationOnMock.getArgument(0);
            consumer.accept(transactionStatus);
            return null;
        }).when(transactionTemplate).executeWithoutResult(isA(Consumer.class));
        int result = orderService.updateOrder(new Order("A111", "UpdatedOrder"));
        assertEquals(1, result);

        verify(persistenceRepository, times(1)).findOrderByOrderNumber("A111");
        ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(persistenceRepository, times(1)).updateOrder(orderEntityArgumentCaptor.capture());
        List<OrderEntity> allOrderEntityArgumentsForUpdateOrder = orderEntityArgumentCaptor.getAllValues();
        assertEquals(1, allOrderEntityArgumentsForUpdateOrder.size());
        assertEquals("UpdatedOrder", allOrderEntityArgumentsForUpdateOrder.get(0).getProduct());
    }

}