package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.SymbolDto;
import com.canada.edu.stocktrading.service.utils.ConvertCalendarUtils;
import com.canada.edu.stocktrading.service.utils.QuickSortUtils;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class StockAPIService {
    static private StockAPIService instance;

    public List<Stock>topThreeStocks;

    public SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

    private StockAPIService() throws IOException {
        // when server first starts
        this.topThreeStocks = new ArrayList<>();
        try{
            List<String> sb = symbolAPIService.symbols.stream().map(SymbolDto::getSymbol).collect(Collectors.toList());
            String[] symbols = sb.toArray(new String[0]);
            List<Stock>stocks = new ArrayList<>();
            for(String s: symbols){
//                Stock stock = YahooFinance.get(s);
                Stock stock = YahooFinance.get(s, ConvertCalendarUtils.getXDaysAgo(1),Interval.DAILY);
                stocks.add(stock);
            }
            // sort the stocks based on today's close price (using getPreviousClosePrice()), in descending order
            QuickSortUtils.quicksort(stocks,0,stocks.size()-1, QuickSortUtils.SortType.DESCENDING);

            //get the name of three stocks having highest close price and convert to an array to pass to YahooFinance get()
            List<String>threeStocks = stocks.subList(0,3).stream().map(Stock::getSymbol).collect(Collectors.toList());
            String[] stocksStr = threeStocks.toArray(new String[0]);
            // loop through these stocks to get closing price within 30 days
            for (int i = 0; i <stocksStr.length; i++) {
                Stock stock = YahooFinance.get(stocksStr[i], ConvertCalendarUtils.getXDaysAgo(180),Interval.DAILY);
                this.topThreeStocks.add(stock);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }

    }

    public static StockAPIService getInstance() throws IOException {
        if(instance==null) {
            synchronized (StockAPIService.class){
                instance = new StockAPIService();
            }
        }

        return instance;
    }
}
