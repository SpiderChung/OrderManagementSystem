package ru.schung.order.service;

import org.springframework.stereotype.Service;
import ru.schung.order.exception.ItemNameNotFoundException;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.repository.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> createOrderItems(List<OrderItemDTO> itemsDTO, Order order) {
        List<OrderItem> items = itemsDTO.stream()
                .map(dto -> mapToOrderItem(dto, order))
                .collect(Collectors.toList());
        orderItemRepository.saveAll(items);
        return items;
    }

    public Long findOrderByItemName(String itemName) {
        return orderItemRepository.findByItemName(itemName)
                .map(orderItem -> orderItem.getOrder().getOrderNumber())
                .orElseThrow(() -> new ItemNameNotFoundException("There isn't item with name " + itemName));
    }

    private OrderItem mapToOrderItem(OrderItemDTO orderItemDTO, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(orderItemDTO.getItemName());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(order);
        return orderItem;
    }
}
