package ru.schung.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.schung.order.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByItemName(String name);
}
