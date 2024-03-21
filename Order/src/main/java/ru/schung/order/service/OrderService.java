package ru.schung.order.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderNumber;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.List;


public interface OrderService {
    public Order createOrder(BigDecimal quantity);
    public Order getMaxTotalOrder(String date);
    public List<Long> getOrdersWithoutItem(String itemId, String startDate, String endDate);


    public OrderNumber requestNumber();
}
