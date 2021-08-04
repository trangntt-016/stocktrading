package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.api.impl.OrderControllerImpl;
import com.canada.edu.stocktrading.dto.OrderFilledDto;
import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.utils.observer.Observer;
import com.canada.edu.stocktrading.utils.observer.PropertyChangedEventArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderWSController implements Observer<OrderControllerImpl> {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    @Autowired
    OrderService orderService;

    OrderControllerImpl orderController;


    public OrderWSController(SimpMessagingTemplate simpMessagingTemplate, OrderControllerImpl orderController){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.orderController = orderController;
        this.orderController.subscribe(this);
    };

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

    @Override
    public void handle(PropertyChangedEventArgs<OrderControllerImpl> args) {
        String message = args.userId + " has just purchased " + args.symbol;
        this.simpMessagingTemplate.convertAndSend("/queue/new-order", message);
    }
}
