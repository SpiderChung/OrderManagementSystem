package ru.schung.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.schung.order.model.Order;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findTopByOrderDateOrderByTotalAmount(Date date);
    List<Order> findByOrderNumberNotAndOrderDateBetween(Long itemName, Date startDate, Date endDate);
}
