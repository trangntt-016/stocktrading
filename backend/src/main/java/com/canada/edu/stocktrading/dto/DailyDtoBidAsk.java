package com.canada.edu.stocktrading.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyDtoBidAsk {
    private BigDecimal bid;

    private BigDecimal ask;

    private BigDecimal spread;

    public void setSpread(){
         this.spread = this.ask.subtract(this.bid);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
                sb.append("bid:").append(this.bid)
                .append(",ask:").append(this.ask)
                .append(",spread:").append(this.spread);
        return sb.toString();
    }
}
