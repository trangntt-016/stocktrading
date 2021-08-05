package com.canada.edu.stocktrading.api.impl;

import com.canada.edu.stocktrading.api.OrderController;
import com.canada.edu.stocktrading.api.exception.BadRequestException;
import com.canada.edu.stocktrading.api.exception.InternalServerException;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.dto.OrderDto;
import com.canada.edu.stocktrading.utils.observer.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderControllerImpl extends Observable<OrderControllerImpl> implements OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody OrderDto order) {
        try{
            Order saved = orderService.save(order);

            if(saved.getOrderSide().name() =="BUY"){
                notifyObservers(this, saved.getUser().getUserId(), saved.getSymbol().getSymbol());
            }

            return responseFactory.success(saved);
        }
        catch(IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Unable to create new order.");
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllOrdersByUserId(String userId) {
        try {
            List<Order> orders = orderService.getAllOrdersByUserId(userId);
            return this.responseFactory.success(orders);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }

    }
}


