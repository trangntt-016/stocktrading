package com.canada.edu.stocktrading.client.api;

import com.canada.edu.stocktrading.service.dto.UserRegisteredDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {
    @PostMapping
    ResponseEntity<?> register(@RequestBody UserRegisteredDto userEntity);
}
