package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.YahooFinanceService;
import com.canada.edu.stocktrading.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class YahooFinanceServiceImpl implements YahooFinanceService {
    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    public List<HistoricalQuoteDto> getHistoricalQuotes(String symbol, int interval) throws IOException {
        List<HistoricalQuoteDto> historicalQuoteDtos = new ArrayList<>();

        if(interval != 1) {
            Calendar from = ConvertTimeUtils.getXDaysAgo(interval);

            Calendar to = ConvertTimeUtils.getToday();

            Stock stock = YahooFinance.get(
                    symbol
                    ,from
                    ,to
                    , Interval.DAILY);

            historicalQuoteDtos = MapperUtils.mapperList(stock.getHistory(),HistoricalQuoteDto.class);
        }
        else{
            Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();


            List<Daily> dailies = dailyRepository.findAllUntilPresentByTimestampAndSymbolId(ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(), 1);

            historicalQuoteDtos = MapperUtils.mapperList(dailies, HistoricalQuoteDto.class);
        }


        return historicalQuoteDtos;
    }
}
