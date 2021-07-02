package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import yahoofinance.Stock;


@Data
@AllArgsConstructor
public class StockAPI {
    private final Stock stock;
}
