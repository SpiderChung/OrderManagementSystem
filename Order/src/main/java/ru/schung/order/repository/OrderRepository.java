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

//    @Query("SELECT o FROM Order_table o JOIN o.orderItems oi WHERE oi.itemName != :itemName AND o.orderDate BETWEEN :startDate AND :endDate")
//    List<Order> findOrdersWithoutItemBetweenDates(Long itemName, Date startDate, Date endDate);

//    @Query("SELECT o.orderNumber FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate " +
//            "AND o.orderNumber NOT IN (SELECT o2.orderNumber FROM Order o2 JOIN o2.orderItems oi WHERE oi.itemId = :itemId)")
//    List<Long> findOrderNumbersWithoutItemBetweenDates(@Param("itemId") Long itemId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
