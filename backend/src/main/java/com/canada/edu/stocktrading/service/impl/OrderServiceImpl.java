package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.model.*;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SymbolService symbolService;

    public Order save(OrderDto order) {
        Symbol symbol = symbolService.getOneBySymbolId(order.getSymbol().getSymbolId());

        order.setSymbol(symbol);

        OrderSide orderSide = order.getOrderSide().equals("BUY")?OrderSide.BUY:OrderSide.SELL;

        OrderType orderType = order.getOrderType().equals("LIMIT")?OrderType.LIMIT:OrderType.MARKET;

        Order newOrder = Order.builder()
                .orderPlaced(new Date())
                .orderSide(orderSide)
                .orderType(orderType)
                .orderStatus(OrderStatus.WORKING)
                .symbol(order.getSymbol())
                .filledQuantity(order.getFilledQuantity())
                .build();

        if (order.getOrderType().equals("LIMIT")) {
            newOrder.setLimitPrice(order.getLimitPrice());
        } else {
            newOrder.setAvgPrice(order.getAvgPrice());
        }

        return orderRepository.save(newOrder);
    }
}
