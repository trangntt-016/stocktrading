package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.config.WebSocketEventListener;
import com.canada.edu.stocktrading.dto.DailyBidAskDto;
import com.canada.edu.stocktrading.dto.DailyDto03MSummary;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class OrderWSController {

    @Autowired
    WebSocketEventListener webSocketEventListener;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private OrderRepository orderRepository;


    public OrderWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedDelay = 3000)
    public void updateOrderStatus() {

        boolean shouldUpdate = false;
        List<Order>orders = orderRepository.findAllByStatus(OrderStatus.WORKING);


        if(orders != null) {
            orders.forEach(o->{
                Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

                Daily matched = dailyRepository.findMatchedByLimitPriceAndSymbolId(10,0,9, o.getLimitPrice(), o.getSymbol().getSymbolId());

                if(matched!=null && matched.getSymbol().getSymbolId().equals(o.getSymbol().getSymbolId())){
                    orderRepository.updateOrderStatus(o.getOrderId(),OrderStatus.FILLED);

                    orderRepository.updateFilledTime(o.getOrderId(),ts);

                    orderRepository.updateAveragePrice(o.getOrderId(), o.getLimitPrice());

                    List<Order>updatedOrders = orderRepository.getAllOrdersByUserId(o.getUser().getUserId());

                    this.simpMessagingTemplate.convertAndSendToUser(o.getUser().getUserId(),"/queue/order",updatedOrders);
                }
            });
        }
    }
}
