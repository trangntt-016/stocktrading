package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.Daily;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyDto03MSummary extends DailyDto {
    private BigDecimal avg_vol_3_months;

    private BigDecimal week_high_52;

    private BigDecimal week_low_52;

    private String symbol;


    public DailyDto03MSummary(Daily daily) {
        super(daily);
        this.avg_vol_3_months = daily.getAvg_vol_3_months();
        this.week_high_52 = daily.getWeek_high_52();
        this.week_low_52 = daily.getWeek_low_52();
        this.symbol = daily.getSymbol().getSymbol();
        this.setChange();
        this.setChangeInPercent();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("dailyId:").append(this.dailyId)
                .append(",timestamp:").append(this.timestamp)
                .append(",price:").append(this.price)
                .append(",open:").append(this.open)
                .append(",high:").append(this.high)
                .append(",low:").append(this.low)
                .append(",close:").append(this.close)
                .append(",volume:").append(this.volume)
                .append(",avg_vol_3_months:").append(this.avg_vol_3_months)
                .append(",prev_close:").append(this.prevClose)
                .append(",week_high_52:").append(this.week_high_52)
                .append(",week_low_52:").append(this.week_low_52)
                .append(",change:").append(this.change)
                .append(",changeInPercent:").append(this.changeInPercent)
                .append(",symbol:").append(this.symbol);
        return sb.toString();
    }

}
