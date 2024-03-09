package ru.schung.order.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.web.bind.annotation.*;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.service.OrderItemService;
import ru.schung.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody List<OrderItemDTO> items) {
        Double quantity = items.stream().map(OrderItemDTO::getQuantity)
                .mapToDouble(Double::doubleValue).sum();
        Order order = orderService.createOrder(quantity);
        orderItemService.createOrderItems(items, order);
        return order;
    }


    @GetMapping("/maxTotal")
    public Long getMaxTotalOrder(@RequestParam String date) {
        return orderService.getMaxTotalOrder(date).getOrderNumber();
    }

    @GetMapping("/withoutItem")
    public List<Long> getOrdersWithoutItem(@RequestParam Long itemId,
                                           @RequestParam String startDate,
                                           @RequestParam String endDate) {
        return orderService.getOrdersWithoutItem(itemId, startDate, endDate);
    }


}

