package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyDtoDetails extends DailyDto {
    private BigDecimal bid;

    private BigDecimal ask;

    private Symbol symbol;

    public DailyDtoDetails(Daily daily) {
        super(daily);
        this.bid = daily.getBid();
        this.ask = daily.getAsk();
        this.symbol = daily.getSymbol();
        this.setChange();
        this.setChangeInPercent();
    }

}
