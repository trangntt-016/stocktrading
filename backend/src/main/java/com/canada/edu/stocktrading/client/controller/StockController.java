package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.service.StockService;
import com.canada.edu.stocktrading.service.SymbolAPIService;
import com.canada.edu.stocktrading.service.dto.ChartDto;
import com.canada.edu.stocktrading.service.dto.SymbolDto;
import com.canada.edu.stocktrading.service.dto.SummaryStockDto;
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
//    public SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();
//
//    @Autowired
//    private StockService stockService;
//
//    public StockController() throws IOException {
//    }
//
//    @GetMapping("/charts-mainpage")
//    public ResponseEntity<List<ChartDto>> getHistoricalQuotes() throws IOException {
//        List<ChartDto>dtos = stockService.getHistoricalQuotesForTopThreeCharts();
//        return ResponseEntity.ok(dtos);
//    }
//
//    @GetMapping("/topthree")
//    public ResponseEntity<List<SummaryStockDto>> getSummaryTopStocks() throws IOException {
//        List<SummaryStockDto>dtos = stockService.getSummaryTopThreeStocks();
//        return ResponseEntity.ok(dtos);
//    }



//    @GetMapping("/topthree")
//    public ResponseEntity<List<String>> getSummaryTopStocks() throws IOException {
//        List<String>dtos = stockService.getSummaryTopThreeStocks();
//        return ResponseEntity.ok(dtos);
//    }
}
