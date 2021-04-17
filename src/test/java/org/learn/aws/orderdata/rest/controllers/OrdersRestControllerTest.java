package org.learn.aws.orderdata.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OrdersRestController.class)
class OrdersRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService mockOrderService;

    @Test
    public void shouldReturnSuccessOrderSearch() throws Exception {
        when(mockOrderService.searchOrder(any()))
                .thenReturn(new Order("ABC", "AWS"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/orders/AAC"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNumber", is("ABC")));
    }

    @Test
    public void shouldSendHttpOkResponseWhenOrderPersisted() throws Exception {
        when(mockOrderService.addOrder(any(Order.class)))
                .thenReturn(0);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Order("ABC", "AWS"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void shouldSendHttpConflictResponseWhenDuplicateOrderAttemptedToPersist() throws Exception {
        when(mockOrderService.addOrder(any(Order.class)))
                .thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Order("ABC", "AWS"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void shouldSendHttpServerErrorResponseWhenPersistenceLayerFails() throws Exception {
        when(mockOrderService.addOrder(any(Order.class)))
                .thenReturn(2);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Order("ABC", "AWS"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}