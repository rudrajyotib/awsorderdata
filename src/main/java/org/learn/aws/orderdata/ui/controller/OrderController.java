package org.learn.aws.orderdata.ui.controller;

import org.learn.aws.orderdata.dataObjects.Order;
import org.learn.aws.orderdata.service.OrderService;
import org.learn.aws.orderdata.ui.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/createorder")
    public String createNewOrder(Model model) {
        model.addAttribute("orderData", new OrderModel("", ""));
        return "order-create";
    }

    @GetMapping("/searchorder")
    public String searchOrder(Model model) {
        model.addAttribute("orderData", new OrderModel("", ""));
        return "order-search";
    }

    @PostMapping("/createorder")
    public ModelAndView acceptNewOrder(@Valid @ModelAttribute("orderData") OrderModel orderModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("order-create", "orderData", orderModel);
        }
        int result = orderService.addOrder(new Order(orderModel.getOrderNumber(), orderModel.getProduct()));
        return new ModelAndView("redirect:/ordercreateresult", "result", result);
    }

    @GetMapping("/ordercreateresult")
    public String orderCreated(@RequestParam("result") String result, Model model) {
        model.addAttribute("result", result);
        return "order-create-result";
    }

}
