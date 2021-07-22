package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Order;

import com.canada.edu.stocktrading.service.dto.OrderDto;

public interface OrderService {
    Order save(OrderDto order);
}
