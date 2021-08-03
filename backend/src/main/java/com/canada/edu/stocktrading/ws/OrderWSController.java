package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.dto.OrderFilledDto;
import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderWSController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    @Autowired
    OrderService orderService;

    public OrderWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedDelay = 3000)
    public void updateOrderStatus() {
        List<Order> orders = orderService.getAllOrdersByStatus(OrderStatus.WORKING);

        orders.forEach(o -> {
            List<OrderFilledDto>dtos = orderService.getAllOrdersAfterFilled(o);
            if(dtos != null){
                this.simpMessagingTemplate.convertAndSendToUser(o.getUser().getUserId(),"/queue/order",dtos.toString());
            }
        });
     }
}
