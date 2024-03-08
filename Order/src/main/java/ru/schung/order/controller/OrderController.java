package ru.schung.order.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.model.OrderNumber;
import ru.schung.order.service.OrderItemService;
import ru.schung.order.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody List<OrderItemDTO> items) {
        Double quantity = items.stream().map(OrderItemDTO::getQuantity).mapToDouble(Double::doubleValue).sum();
        System.out.println(quantity);
        Order order = orderService.createOrder(quantity);
        return order;
    }


    @GetMapping("/maxTotal")
    public Order getMaxTotalOrder(@RequestParam String date) {
        return orderService.getMaxTotalOrder(convertToDate(date));
    }

//    @GetMapping("/withoutItem")
//    public List<Long> getOrdersWithoutItem(@RequestParam Long itemId,
//                                           @RequestParam String startDate,
//                                           @RequestParam String endDate) {
//        return orderService.getOrdersWithoutItem(itemId, startDate, endDate);
//    }

    private Date convertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
        return date;
    }
}

