package ru.schung.order;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItem;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.repository.OrderItemRepository;
import ru.schung.order.service.OrderItemService;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void createOrderItemsTest() {
        Order order = new Order();
        List<OrderItemDTO> itemsDTO = List.of(
                new OrderItemDTO("Item1", 1.0),
                new OrderItemDTO("Item2", 2.0)
        );

        List<OrderItem> expectedItems = itemsDTO.stream()
                .map(dto -> {
                    OrderItem item = new OrderItem();
                    item.setItemName(dto.getItemName());
                    item.setQuantity(dto.getQuantity());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        when(orderItemRepository.saveAll(anyList())).thenReturn(expectedItems);

        List<OrderItem> createdItems = orderItemService.createOrderItems(itemsDTO, order);

        assertThat(createdItems).hasSize(itemsDTO.size());
        for (int i = 0; i < createdItems.size(); i++) {
            assertThat(createdItems.get(i).getItemName()).isEqualTo(itemsDTO.get(i).getItemName());
            assertThat(createdItems.get(i).getQuantity()).isEqualTo(itemsDTO.get(i).getQuantity());
            assertThat(createdItems.get(i).getOrder()).isEqualTo(order);
        }

        verify(orderItemRepository).saveAll(anyList());
    }
}