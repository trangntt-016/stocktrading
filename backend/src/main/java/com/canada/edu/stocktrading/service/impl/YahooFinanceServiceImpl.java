package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.service.YahooFinanceService;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Service
public class YahooFinanceServiceImpl implements YahooFinanceService {
    public List<HistoricalQuoteDto> getHistoricalQuotes(String symbol, int interval) throws IOException {
        Calendar from = ConvertTimeUtils.getXDaysAgo(interval);

        Calendar to = ConvertTimeUtils.getToday();

        Stock stock = YahooFinance.get(
                symbol
                ,from
                ,to
                , Interval.DAILY);

        List<HistoricalQuoteDto> historicalQuoteDtos = MapperUtils.mapperList(stock.getHistory(),HistoricalQuoteDto.class);

        return historicalQuoteDtos;
    }
}
