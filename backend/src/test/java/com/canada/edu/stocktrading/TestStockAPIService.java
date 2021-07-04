package com.canada.edu.stocktrading;

import com.canada.edu.stocktrading.service.StockAPIService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockStats;

import java.io.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@SpringBootTest
public class TestStockAPIService {

    @Test
    public void testGetThreePopularStocks() throws JSONException, IOException {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()).minusDays(30);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            Calendar from = new Calendar.Builder().setInstant(out).build(); //30 days ago
    Calendar to = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
    Map<String, Stock> stocks = YahooFinance.get(
            new String[]{"AMZN"}
            ,from
            ,to
            , Interval.DAILY);
    DateTimeFormatter fm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        for (int i = 0; i <1; i++) {
        try {
        Stock element = stocks.get("AMZN");
        BigDecimal element_quote = element.getQuote().getPreviousClose();
        System.out.println("previous close"+element_quote);
        StockStats element_stats = element.getStats();
        List<HistoricalQuote> hist_list = element.getHistory();
        hist_list.forEach(item->{
            System.out.println("Symbol: "+item.getSymbol());

            Instant instant = item.getDate().toInstant();
            ZoneId z = ZoneId.of( "America/Toronto" );
            ZonedDateTime zdt = instant.atZone( z );
            LocalDateTime ld = zdt.toLocalDateTime();
            System.out.println("date: "+ fm.format(ld));
            System.out.println("open: "+item.getOpen());
            System.out.println("close: "+item.getClose());
            System.out.println("high: "+item.getHigh());
            System.out.println("adfClose: "+item.getAdjClose());
            System.out.println("volumn"+item.getVolume());
            System.out.println("\n");
        });
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
    }
}
