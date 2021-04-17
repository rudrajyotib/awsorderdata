package org.learn.aws.orderdata.rest.controllers;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class OrdersRestController {

    private static final Map<Integer, HttpStatus> resultCodeToResponseCodeMapping = new HashMap<>(3) {
        {
            put(0, HttpStatus.OK);
            put(1, HttpStatus.CONFLICT);
            put(2, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "orderId") String orderId) {
        return new ResponseEntity<>(orderService.searchOrder(orderId), HttpStatus.OK);
    }

    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        int result = orderService.addOrder(order);
        return new ResponseEntity<>(Integer.toString(result), resultCodeToResponseCodeMapping.get(result));
    }

}
