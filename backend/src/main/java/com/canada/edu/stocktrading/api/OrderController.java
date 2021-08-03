package com.canada.edu.stocktrading.api;

import com.canada.edu.stocktrading.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
public interface OrderController {
    @PostMapping
    ResponseEntity<?> createNewOrder(@RequestBody OrderDto order);

    @GetMapping
    ResponseEntity<?> findAllOrdersByUserId(@RequestParam String userId);
}
