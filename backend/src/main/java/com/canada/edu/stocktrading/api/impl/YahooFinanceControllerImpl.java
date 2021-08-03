package com.canada.edu.stocktrading.api.impl;

import com.canada.edu.stocktrading.api.YahooFinanceController;
import com.canada.edu.stocktrading.api.exception.InternalServerException;
import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.impl.YahooFinanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/yahoo-finance")
public class YahooFinanceControllerImpl implements YahooFinanceController {
    private final ResponseFactory responseFactory;

    @Autowired
    private YahooFinanceServiceImpl yahooFinanceService;

    public YahooFinanceControllerImpl(ResponseFactory responseFactory) {
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
