package ru.schung.order;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import ru.schung.order.model.Order;
import ru.schung.order.model.OrderNumber;
import ru.schung.order.repository.OrderRepository;
import ru.schung.order.service.OrderService;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderTest() {
        // Подготовка
        Long expectedOrderNumber = 123456789L;
        String expectedDateStr = "240310";
        Double expectedQuantity = 100.0;

        OrderNumber mockOrderNumber = new OrderNumber(expectedOrderNumber, expectedDateStr);
        when(restTemplate.getForObject(anyString(), eq(OrderNumber.class)))
                .thenReturn(mockOrderNumber);

        Order expectedOrder = new Order();
        expectedOrder.setOrderNumber(expectedOrderNumber);
        expectedOrder.setOrderDate(OrderService.convertStringToDate(expectedDateStr));
        expectedOrder.setTotalAmount(expectedQuantity);

        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        Order resultOrder = orderService.createOrder(expectedQuantity);

        assertThat(resultOrder).isNotNull();
        assertThat(resultOrder.getOrderNumber()).isEqualTo(expectedOrderNumber);
        assertThat(resultOrder.getTotalAmount()).isEqualTo(expectedQuantity);
        assertThat(resultOrder.getOrderDate()).isEqualTo(OrderService.convertStringToDate(expectedDateStr));

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void getMaxTotalOrderTest() {
        String dateStr = "240310";
        Date date = OrderService.convertStringToDate(dateStr);
        Order expectedOrder = new Order();
        expectedOrder.setOrderNumber(123456789L);

        when(orderRepository.findTopByOrderDateOrderByTotalAmount(date)).thenReturn(expectedOrder);

        Order resultOrder = orderService.getMaxTotalOrder(dateStr);

        assertThat(resultOrder).isNotNull();
        assertThat(resultOrder.getOrderNumber()).isEqualTo(expectedOrder.getOrderNumber());
    }

    @Test
    void getOrdersWithoutItemTest() {
        Long itemId = 1L;
        String startDateStr = "240301";
        String endDateStr = "240310";
        Date startDate = OrderService.convertStringToDate(startDateStr);
        Date endDate = OrderService.convertStringToDate(endDateStr);

        List<Order> orders = List.of(new Order(), new Order());
        orders.get(0).setOrderNumber(123456789L);
        orders.get(1).setOrderNumber(987654321L);

        when(orderRepository.findByOrderNumberNotAndOrderDateBetween(eq(itemId), eq(startDate), eq(endDate)))
                .thenReturn(orders);

        List<Long> resultOrderNumbers = orderService.getOrdersWithoutItem(itemId, startDateStr, endDateStr);

        assertThat(resultOrderNumbers).isNotNull().hasSize(2)
                .containsExactlyInAnyOrder(123456789L, 987654321L);
    }
}