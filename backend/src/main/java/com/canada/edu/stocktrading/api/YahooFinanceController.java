package com.canada.edu.stocktrading.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/yahoo-finance")
public interface YahooFinanceController {
    @GetMapping
    ResponseEntity<?> getHistoricalQuotes(@RequestParam(required = true) String symbol, @RequestParam(required = true) int interval);
}
