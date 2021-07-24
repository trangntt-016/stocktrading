package com.canada.edu.stocktrading.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
public class HistoricalQuoteDto{
    private String symbol;
    private Calendar date;
    private BigDecimal close;
}
