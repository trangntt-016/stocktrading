package com.canada.edu.stocktrading.api;

import com.canada.edu.stocktrading.dto.UserRegisteredDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserApi {
    @PostMapping
    ResponseEntity<?> register(@RequestBody UserRegisteredDto userEntity);

    @GetMapping("/{userId}/position")
    ResponseEntity<?> getPositionByUserIdAndSymbolId(@PathVariable("userId") String userId);
}
