package org.learn.aws.orderdata.rest.controllers;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class OrdersRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "orderId") String orderId) {
        return new ResponseEntity<>(orderService.searchOrder(orderId), HttpStatus.OK);
    }

    @PostMapping("/orders/add")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

}
