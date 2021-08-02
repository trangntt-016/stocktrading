package com.canada.edu.stocktrading.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/symbol")
public interface SymbolController {
    @GetMapping
    ResponseEntity<?> getAllSymbols();
}
