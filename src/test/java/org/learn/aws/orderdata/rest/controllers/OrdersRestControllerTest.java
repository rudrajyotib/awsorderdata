package org.learn.aws.orderdata.rest.controllers;

import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

}