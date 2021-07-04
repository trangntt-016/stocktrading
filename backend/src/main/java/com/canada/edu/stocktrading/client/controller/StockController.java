package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.model.StockAPI;
import com.canada.edu.stocktrading.service.StockAPIService;
import com.canada.edu.stocktrading.service.StockService;
import com.canada.edu.stocktrading.service.SymbolAPIService;
import com.canada.edu.stocktrading.service.dto.HistoricalQuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/charts-mainpage")
    public ResponseEntity<List<List<HistoricalQuoteDto>>> getHistoricalQuotes() throws IOException {
        List<List<HistoricalQuoteDto>>dtos = stockService.getHistoricalQuotesForTopThreeCharts();
        return ResponseEntity.ok(dtos);
    }
}
