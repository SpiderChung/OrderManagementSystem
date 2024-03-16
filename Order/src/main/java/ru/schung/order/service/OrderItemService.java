package ru.schung.order.service;

import org.springframework.stereotype.Service;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;

import java.util.List;

@Service
public interface OrderItemService {
    public List<OrderItem> createOrderItems(List<OrderItemDTO> itemsDTO, Order order);
    public Long findOrderByItemName(String itemName);
}
