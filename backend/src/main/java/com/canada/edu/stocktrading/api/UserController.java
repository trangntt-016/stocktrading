package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.dto.UserRegisteredDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserController {
    @PostMapping
    ResponseEntity<?> register(@RequestBody UserRegisteredDto userEntity);
}
