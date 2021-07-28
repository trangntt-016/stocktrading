package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Order;

import com.canada.edu.stocktrading.dto.OrderDto;

import java.util.List;

public interface OrderService {
    Order save(OrderDto order);

    List<Order> getAllOrdersByUserId (String userId);
}
