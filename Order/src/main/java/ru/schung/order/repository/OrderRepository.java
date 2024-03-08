package ru.schung.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.schung.order.model.Order;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findTopByOrderDateOrderByTotalAmount(Date date);

//    List<Long> findOrdersWithoutItem(Long itemId, String startDate, String endDate);
}
