package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.model.*;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.dto.OrderDto;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SymbolService symbolService;

    @Autowired
    DailyRepository dailyRepository;

    public Order save(OrderDto order) {
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        BigDecimal currentPrice = dailyRepository.findCurrentPriceBySymbolId(ts, order.getSymbol().getSymbolId());

        OrderType orderType = order.getOrderType().equals("LIMIT") ? OrderType.LIMIT : OrderType.MARKET;

        BigDecimal limitPrice = (orderType.name().equals("LIMIT")) ? order.getLimitPrice() : currentPrice;

        OrderStatus status = (currentPrice == limitPrice) ? OrderStatus.FILLED : OrderStatus.WORKING;

        OrderSide orderSide = order.getOrderSide().equals("BUY") ? OrderSide.BUY : OrderSide.SELL;

        Order newOrder = Order.builder()
                .orderPlaced(ConvertTimeUtils.convertCurrentTimeTo14July())
                .orderSide(orderSide)
                .orderType(orderType)
                .orderStatus(status)
                .symbol(order.getSymbol())
                .filledQuantity(order.getFilledQuantity())
                .limitPrice(limitPrice)
                .build();

        if(newOrder.getOrderStatus().equals("FILLED")){
            // supposing that commission = 0
            newOrder.setAvgPrice(limitPrice);
        }

        return orderRepository.save(newOrder);
    }
}
