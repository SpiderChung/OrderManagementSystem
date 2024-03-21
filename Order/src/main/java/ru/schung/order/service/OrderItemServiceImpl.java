package ru.schung.order.service;

import org.springframework.stereotype.Service;
import ru.schung.order.exception.ItemNameNotFoundException;
import ru.schung.order.mapper.OrderItemDtoOrderMapper;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.repository.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemDtoOrderMapper mapper;


    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemDtoOrderMapper mapper) {
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderItem> createOrderItems(List<OrderItemDTO> itemsDTO, Order order) {
        List<OrderItem> items = itemsDTO.stream()
                .map(item -> mapper.OrderItemDtoToOrderItem(item, order))
                .collect(Collectors.toList());
        orderItemRepository.saveAll(items);
        return items;
    }
    @Override
    public Long findOrderByItemName(String itemName) {
        return orderItemRepository.findByItemName(itemName)
                .map(orderItem -> orderItem.getOrder().getOrderNumber())
                .orElseThrow(() -> new ItemNameNotFoundException("There isn't item with name " + itemName));
    }


}
