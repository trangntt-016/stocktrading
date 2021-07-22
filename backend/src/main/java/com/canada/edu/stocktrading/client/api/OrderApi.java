package com.canada.edu.stocktrading.client.api;

import com.canada.edu.stocktrading.service.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public interface OrderApi {
    @PostMapping
    ResponseEntity<?> order(@RequestBody OrderDto order);
}
