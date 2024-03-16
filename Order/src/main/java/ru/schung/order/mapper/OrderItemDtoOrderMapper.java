package ru.schung.order.mapper;

import org.mapstruct.Mapper;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;

@Mapper
public interface OrderItemDtoOrderMapper {
    public OrderItem OrderItemDtoToOrderItem(OrderItemDTO orderItemDTO, Order order);
}
