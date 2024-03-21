package ru.schung.order.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.schung.order.exception.ItemNameNotFoundException;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderNumber;
import ru.schung.order.repository.OrderRepository;
import ru.schung.order.service.OrderItemService;
import ru.schung.order.service.OrderService;
import ru.schung.order.utils.OrderUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService, RestTemplate restTemplate, RetryTemplate retryTemplate) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
    }

    @Override
    public Order createOrder(BigDecimal quantity) {
        OrderNumber orderNumberResponse = requestNumber();
        Order order = new Order();
        assert orderNumberResponse != null;
        order.setOrderNumber(orderNumberResponse.getOrderNumber());
        Date date = OrderUtils.convertStringToDate(orderNumberResponse.getDateTime());
        order.setOrderDate(date);
        order.setTotalAmount(quantity);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getMaxTotalOrder(String date) {
      return orderRepository.findTopByOrderDateOrderByTotalAmount(OrderUtils.convertToDate(date));
    }

    @Override
    public List<Long> getOrdersWithoutItem(String itemId, String startDate, String endDate) throws ItemNameNotFoundException {
        Long orderNumber = orderItemService.findOrderByItemName(itemId);
        return orderRepository.findByOrderNumberNotAndOrderDateBetween(orderNumber,
                OrderUtils.convertToDate(startDate), OrderUtils.convertStringToDate(endDate)).stream()
                .map(Order::getOrderNumber).toList();
    }

    @Override
//    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}",
//            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public OrderNumber requestNumber() {
        return retryTemplate.execute(context -> {
            String url = "http://localhost:8080/api/order/generate";
            return restTemplate.getForObject(url, OrderNumber.class);
        });
    }
}
