package ru.schung.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
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

    public Order getMaxTotalOrder(Date date) {
      return orderRepository.findTopByOrderDateOrderByTotalAmount(date);
    }
//
//    public List<Long> getOrdersWithoutItem(Long itemId, String startDate, String endDate) {
//       return orderRepository.findOrdersWithoutItem(itemId, startDate, endDate);
//    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd"); // Формат строки даты
        try {
            return dateFormat.parse(dateString); // Парсим строку в объект Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

