package com.canada.edu.stocktrading.service.dto;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;

import java.math.BigDecimal;


public class DailyDetailsDto extends DailyDto {
    private BigDecimal bid;

    private BigDecimal ask;

    private Symbol symbol;

    public DailyDetailsDto(Daily daily) {
        super(daily);
        this.bid = daily.getBid();
        this.ask = daily.getAsk();
        this.symbol = daily.getSymbol();
        this.setChange();
        this.setChangeInPercent();
    }

}
