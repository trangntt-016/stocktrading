package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.OrderFilledDto;
import com.canada.edu.stocktrading.model.Order;

import com.canada.edu.stocktrading.dto.OrderDto;
import com.canada.edu.stocktrading.model.OrderStatus;

import java.util.List;

public interface OrderService {
    Order save(OrderDto order);

    List<Order> getAllOrdersByUserId (String userId);

    List<OrderFilledDto> getAllOrdersAfterFilled(Order o);

    List<Order> getAllOrdersByStatus (OrderStatus status);

    List<Order> resetOrderData();
}
