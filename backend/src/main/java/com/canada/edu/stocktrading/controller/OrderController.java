package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.service.OrderService;
import com.canada.edu.stocktrading.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping
    public ResponseEntity<?> order(OrderDto order) {
        try{
            Order saved = orderService.save(order);
            return responseFactory.success(saved);
        }
        catch(IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Unable to create new order.");
        }
    }
}
