package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.api.YahooFinanceApi;
import com.canada.edu.stocktrading.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.YahooFinanceService;
import com.canada.edu.stocktrading.service.impl.YahooFinanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/yahoo-finance")
public class YahooFinanceController implements YahooFinanceApi {
    private final ResponseFactory responseFactory;

    @Autowired
    private YahooFinanceServiceImpl yahooFinanceService;

    public YahooFinanceController(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    public ResponseEntity<?> getHistoricalQuotes(String symbol, int interval) {
        try{
            List<HistoricalQuoteDto> quotes = yahooFinanceService.getHistoricalQuotes(symbol, interval);
            return this.responseFactory.success(quotes);
        }
        catch(Exception ex){
            throw new InternalServerException("Unable to get historical quotes from symbol " + symbol);
        }
    }
}
