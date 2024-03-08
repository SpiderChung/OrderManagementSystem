package ru.schung.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.schung.order.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
