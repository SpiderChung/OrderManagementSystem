package ru.schung.order.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.schung.order.exception.ItemNameNotFoundException;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderNumber;
import ru.schung.order.repository.OrderRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(Double quantity) {
        String url = "http://localhost:8080/api/order/generate";
        OrderNumber orderNumberResponse = restTemplate.getForObject(url, OrderNumber.class);
        Order order = new Order();
        assert orderNumberResponse != null;
        order.setOrderNumber(orderNumberResponse.getOrderNumber());
        Date date = convertStringToDate(orderNumberResponse.getDateTime());
        order.setOrderDate(date);
        order.setTotalAmount(quantity);
        orderRepository.save(order);
        return order;
    }

    public Order getMaxTotalOrder(String date) {
      return orderRepository.findTopByOrderDateOrderByTotalAmount(convertToDate(date));
    }

    public List<Long> getOrdersWithoutItem(String itemId, String startDate, String endDate) throws ItemNameNotFoundException {
        Long orderNumber = orderItemService.findOrderByItemName(itemId);
        return orderRepository.findByOrderNumberNotAndOrderDateBetween(orderNumber,
                convertToDate(startDate), convertStringToDate(endDate)).stream()
                .map(Order::getOrderNumber).toList();
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Date convertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
        return date;
    }
}

