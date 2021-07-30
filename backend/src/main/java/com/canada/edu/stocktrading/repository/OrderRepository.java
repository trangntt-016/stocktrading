package com.canada.edu.stocktrading.repository;


import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderSide;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.model.Symbol;
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

    @Query("SELECT o FROM Order o WHERE o.user.userId =:userId ORDER BY o.orderPlaced DESC")
    List<Order> getAllOrdersByUserId(String userId);

    @Query("SELECT DISTINCT o.symbol FROM Order o WHERE o.user.userId =:userId")
    List<Symbol> getAllOrderedSymbolsByUserId(String userId);

    @Query("SELECT o FROM Order o WHERE o.orderStatus =:status")
    List<Order> findAllByStatus(OrderStatus status);

    @Query("SELECT SUM(o.filledQuantity) FROM Order o WHERE o.orderStatus =:status AND o.orderSide =:side AND o.symbol.symbolId =:symbolId AND o.user.userId =:userId GROUP BY o.symbol.symbolId")
    Integer calcNumberOfOrders(OrderStatus status, OrderSide side, Integer symbolId, String userId);

    @Query(value = "SELECT SUM(total) FROM (SELECT filled_quantity*avg_price AS total FROM orders WHERE symbol_id =:symbolId AND user_id =:userId AND order_side =:side AND order_status = 'FILLED') AS asliasT", nativeQuery = true)
    BigDecimal calcTotalAmountOfOrdersBySymbolUserSide(String side, Integer symbolId, String userId);
}
