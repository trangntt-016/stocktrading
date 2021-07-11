package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import yahoofinance.Stock;

import java.util.List;


@Data
@AllArgsConstructor
public class StockAPI {
    private static final String[] stocks = new String[]{
    };
    private final Stock stock;
}

