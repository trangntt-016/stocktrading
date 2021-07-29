package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.OrderFilledDto;
import com.canada.edu.stocktrading.model.*;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.UserRepository;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.dto.OrderDto;
import com.canada.edu.stocktrading.service.UserService;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SymbolService symbolService;

    @Autowired
    DailyRepository dailyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public Order save(OrderDto order) {
        Optional<User> user = userRepository.findById(order.getUserId());

        if(user.isEmpty()) {
            throw new IllegalArgumentException("Unable to find user with id " + order.getUserId());
        }

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
                .user(user.get())
                .build();

        if(newOrder.getOrderStatus().equals("FILLED")){
            // supposing that commission = 0
            newOrder.setAvgPrice(limitPrice);
        }
        else{
            newOrder.setAvgPrice(new BigDecimal(0));
        }

        return orderRepository.save(newOrder);
    }

    public List<Order> getAllOrdersByUserId (String userId) {
        if (!userService.isUserIdValid(userId)){
            throw new IllegalArgumentException("Unable to find user with id " + userId);
        }
        return orderRepository.getAllOrdersByUserId(userId);
    }

    public List<OrderFilledDto> getAllOrdersAfterFilled (Order o) {
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        Daily matched = dailyRepository.findMatchedByLimitPriceAndSymbolId(ts.toLocalDateTime().getHour(),ts.toLocalDateTime().getMinute(),ts.toLocalDateTime().getSecond(),o.getLimitPrice() ,o.getSymbol().getSymbolId());

        if(matched!=null){
            orderRepository.updateOrderStatus(o.getOrderId(),OrderStatus.FILLED);

            orderRepository.updateFilledTime(o.getOrderId(),ts);

            orderRepository.updateAveragePrice(o.getOrderId(), o.getLimitPrice());

            List<Order>updatedOrders = orderRepository.getAllOrdersByUserId(o.getUser().getUserId());

            List<OrderFilledDto> dtos = MapperUtils.mapperList(updatedOrders, OrderFilledDto.class);

            return dtos;
        }
        return null;
    }

    public List<Order> getAllOrdersByStatus (OrderStatus status) {
        return orderRepository.findAllByStatus(status);
    }

}
