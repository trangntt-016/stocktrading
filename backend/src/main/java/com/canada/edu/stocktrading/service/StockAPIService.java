package com.canada.edu.stocktrading.service;

import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockStats;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StockAPIService {
    static private List<String>topStocks;


    private SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

    StockAPIService() throws IOException {
        List<String> sb = symbolAPIService.symbols;
        String[] symbols = sb.toArray(new String[0]);
        Calendar from = new Calendar.Builder().setInstant(new Date()).build(); //30 days ago
        Calendar to = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
        for(String s: symbols){
            Stock stock = YahooFinance.get(
                    s,from,to,Interval.DAILY
            );

        }
    }

}


//    Calendar from = new Calendar.Builder().setInstant(System.currentTimeMillis()-2592000).build(); //30 days ago
//    Calendar to = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
//    Map<String, Stock> stocks = YahooFinance.get(
//            symbols
//            ,from
//            ,to
//            , Interval.DAILY);
//    DateTimeFormatter fm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
//        for (int i = 0; i <symbols.length; i++) {
//        try {
//        Stock element = stocks.get(symbols[i]);
//        BigDecimal element_quote = element.getQuote().getPreviousClose();
//        System.out.println("previous close"+element_quote);
//        StockStats element_stats = element.getStats();
//        List<HistoricalQuote> hist_list = element.getHistory();
//        hist_list.forEach(item->{
//        System.out.println("Symbol: "+item.getSymbol());
//
//        Instant instant = item.getDate().toInstant();
//        ZoneId z = ZoneId.of( "America/Montreal" );
//        ZonedDateTime zdt = instant.atZone( z );
//        LocalDateTime ld = zdt.toLocalDateTime();
//        System.out.println("date: "+ fm.format(ld));
//        System.out.println("open: "+item.getOpen());
//        System.out.println("close: "+item.getClose());
//        System.out.println("high: "+item.getHigh());
//        System.out.println("adfClose: "+item.getAdjClose());
//        System.out.println("volumn"+item.getVolume());
//        System.out.println("\n");
//        });
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        }