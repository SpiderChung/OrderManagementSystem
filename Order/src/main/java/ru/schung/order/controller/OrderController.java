package ru.schung.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.schung.order.exception.ItemNameNotFoundException;
import ru.schung.order.exception.NoOrdersException;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.service.OrderItemService;
import ru.schung.order.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Operation(
            summary = "Создание заказа",
            description = "Создает заказ с указанными товарами"
    )
    @PostMapping("/create")
    public Order createOrder(@RequestBody List<OrderItemDTO> items) {
        Double quantity = items.stream().map(OrderItemDTO::getQuantity)
                .mapToDouble(Double::doubleValue).sum();
        Order order = orderService.createOrder(quantity);
        orderItemService.createOrderItems(items, order);
        return order;
    }


    @Operation(
            summary = "Максимальная сумма заказа",
            description = "Возвращает номер заказа с наибольшей суммой " +
                    "за указанную дату"
    )
    @GetMapping("/maxTotal")
    public Long getMaxTotalOrder(@RequestParam String date) {
        Order order = orderService.getMaxTotalOrder(date);
        return Optional.ofNullable(order)
                .orElseThrow(() -> new NoOrdersException("There aren't orders for date " + date))
                .getOrderNumber();
    }

    @Operation(
            summary = "Заказы без указанного товара",
            description = "Возвращает перечень номеров заказов без указанного товара" +
                    "в необходимом промежутке времени"
    )
    @GetMapping("/withoutItem")
    public List<Long> getOrdersWithoutItem(@RequestParam String itemName,
                                           @RequestParam String startDate,
                                           @RequestParam String endDate) {
        return orderService.getOrdersWithoutItem(itemName, startDate, endDate);
    }

    @ExceptionHandler(ItemNameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleItemNameException(ItemNameNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NoOrdersException.class)
    public ResponseEntity<String> handleNoOrdersException(NoOrdersException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


}

