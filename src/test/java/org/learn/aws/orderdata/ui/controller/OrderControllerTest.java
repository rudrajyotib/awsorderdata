package org.learn.aws.orderdata.ui.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.learn.aws.orderdata.service.OrderService;
import org.learn.aws.orderdata.ui.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService mockOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Test

    public void shouldTakeUserToOrderCreatePage() throws Exception {
        this.mockMvc.perform(get("/createorder"))
                .andExpect(status().isOk())
                .andExpect(view().name("order-create"));
    }

    @Test
    public void shouldSaveNewOrder() throws Exception {

        when(mockOrderService.addOrder(argThat(order -> {
            Assertions.assertEquals("AAA", order.getOrderNumber());
            Assertions.assertEquals("BB", order.getProduct());
            return true;
        }))).thenReturn(0);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createorder")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("orderNumber", "AAA")
                .param("product", "BB")
                .sessionAttr("orderData", new OrderModel("AAA", "BB")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ordercreateresult?result=0"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("result"));
    }

    @Test
    public void shouldNotSaveNewOrderIfOrderNumberIsNotProperFormat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createorder")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("orderNumber", "AA")
                .param("product", "BB")
                .sessionAttr("orderData", new OrderModel("AA", "BB")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("result"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderData"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("orderData", "orderNumber"));
    }

    @Test
    public void shouldNotSaveNewOrderIfProductIsEmpty() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createorder")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("orderNumber", "AA")
                .param("product", "")
                .sessionAttr("orderData", new OrderModel("AA", "")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("result"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderData"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("orderData", "product"));
    }

    @Test
    public void shouldGoToOrderCreateResultPage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/ordercreateresult?result=0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("result", "0"));
    }

}