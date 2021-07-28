package com.canada.edu.stocktrading.repository;


import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus =:status WHERE o.orderId =:orderId")
    void updateOrderStatus(Integer orderId, OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.filledTime =:now WHERE o.orderId =:orderId")
    void updateFilledTime(Integer orderId, Timestamp now);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.avgPrice =:avgPrice WHERE o.orderId =:orderId")
    void updateAveragePrice(Integer orderId, BigDecimal avgPrice);

    @Query("SELECT o FROM Order o WHERE o.user.userId =:userId")
    List<Order> getAllOrdersByUserId(String userId);

    @Query("SELECT o FROM Order o WHERE o.orderStatus =:status")
    List<Order> findAllByStatus(OrderStatus status);
}
