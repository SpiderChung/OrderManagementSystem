package ru.schung.generateordernumber.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.schung.generateordernumber.model.OrderNumber;
import ru.schung.generateordernumber.service.OrderNumberService;
import ru.schung.generateordernumber.service.OrderNumberServiceImpl;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderNumberController {
    private final OrderNumberService orderNumberService;

    @Operation(
            summary = "Генерация номера заказа",
            description = "Генерирует номер и дату заказа"
    )
    @GetMapping("/generate")
    public OrderNumber generateNumber() {
        return orderNumberService.createOrderNumber();
    }
}
