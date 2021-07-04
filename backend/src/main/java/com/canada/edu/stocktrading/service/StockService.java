package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    final private SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

    final private StockAPIService stockAPIInstance = StockAPIService.getInstance();

    public StockService() throws IOException { }

    public List<List<HistoricalQuoteDto>>getHistoricalQuotesForTopThreeCharts() throws IOException {
        List<Stock>topThreeStocks = stockAPIInstance.topThreeStocks;
        List<List<HistoricalQuoteDto>>dtos = new ArrayList<>();
        for(Stock s: topThreeStocks){
            dtos.add(MapperUtils.mapperList(s.getHistory(),HistoricalQuoteDto.class));
        }
        return dtos;
    }
}
