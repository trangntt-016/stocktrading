package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.Daily;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class DailyDtoPriceChange {
    private String symbol;

    private BigDecimal price;

    private BigDecimal change;

    private BigDecimal changeInPercent;

    private BigDecimal prevClose;

    public DailyDtoPriceChange(Daily daily) {
        this.symbol = daily.getSymbol().getSymbol();

        this.price = daily.getPrice();

        this.prevClose = daily.getPrevClose();

        this.change = this.price.subtract(this.prevClose);

        this.changeInPercent = this.change.multiply(new BigDecimal(100)).divide(this.price, RoundingMode.HALF_UP);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("DailyDtoPriceChange(symbol=").append(this.symbol)
                .append(",price=").append(this.price)
                .append(",change=").append(this.change)
                .append(",changeInPercent=").append(this.changeInPercent)
                .append(",prevClose=").append(this.prevClose);

        return sb.toString();
    }

}
