package com.canada.edu.stocktrading.api;

import com.canada.edu.stocktrading.dto.UserAuthRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserController {
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserAuthRequestDto userEntity);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserAuthRequestDto userLogin);
}
