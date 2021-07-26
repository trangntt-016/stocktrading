package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;

import java.util.List;

public interface YahooFinanceService {
    List<HistoricalQuoteDto> getHistoricalQuotes(String symbol, int interval) throws Exception;
}
