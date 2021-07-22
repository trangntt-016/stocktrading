package com.canada.edu.stocktrading.repository;


import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

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

}
