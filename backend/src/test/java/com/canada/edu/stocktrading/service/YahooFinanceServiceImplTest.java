package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.HistoricalQuoteDto;
import com.canada.edu.stocktrading.service.impl.YahooFinanceServiceImpl;
import com.canada.edu.stocktrading.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.MapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class YahooFinanceServiceImplTest {
    private final String symbol = "AMZN";

    @InjectMocks
    private YahooFinanceServiceImpl yahooFinanceService;

    @Test
    public void whenGetHistoricalQuote_byValidSymbol_thenReturnData() throws Exception {
        Calendar from = ConvertTimeUtils.getXDaysAgo(30); //30 days ago
        Calendar to = ConvertTimeUtils.getToday();
        Stock stock = YahooFinance.get(
                this.symbol
                ,from
                ,to
                , Interval.DAILY);
        List<HistoricalQuoteDto> historicalQuoteDtos = MapperUtils.mapperList(stock.getHistory(),HistoricalQuoteDto.class);

        List<HistoricalQuoteDto> mockObject = yahooFinanceService.getHistoricalQuotes(this.symbol,30);

        assertThat(historicalQuoteDtos).isEqualTo(mockObject);

        assertThat(stock.getSymbol()).isEqualTo(this.symbol);
    }
}