package com.canada.edu.stocktrading.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/symbol")
public interface SymbolApi {
    @GetMapping
    ResponseEntity<?> getAllSymbols();
}
