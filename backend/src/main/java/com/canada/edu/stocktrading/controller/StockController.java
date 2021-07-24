package com.canada.edu.stocktrading.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
