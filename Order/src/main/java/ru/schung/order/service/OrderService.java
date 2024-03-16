package ru.schung.order.service;

import org.springframework.stereotype.Service;
import ru.schung.order.model.Order;

import java.util.List;

@Service
public interface OrderService {
    public Order createOrder(Double quantity);
    public Order getMaxTotalOrder(String date);
    public List<Long> getOrdersWithoutItem(String itemId, String startDate, String endDate);
}
