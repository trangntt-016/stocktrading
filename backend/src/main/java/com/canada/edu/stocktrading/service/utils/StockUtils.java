package com.canada.edu.stocktrading.service.utils;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class StockUtils {
    public static Stock findStock(String stock){
        try{
            return YahooFinance.get(stock);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }


    public static List<Stock>findStocks(List<String>stocks){
        return stocks.stream().map(StockUtils::findStock).collect(Collectors.toList());
    }
}
