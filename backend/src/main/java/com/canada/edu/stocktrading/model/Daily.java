package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dailies")
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Integer dailyId;

    @Column(name = "timestamp",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "open",nullable = false)
    private BigDecimal open;

    @Column(name = "high",nullable = false)
    private BigDecimal high;

    @Column(name = "low",nullable = false)
    private BigDecimal low;

    @Column(name = "close",nullable = false)
    private BigDecimal close;

    @Column(name = "volume",nullable = false)
    private BigDecimal volume;

    @Column(name = "avg_vol_3_months",nullable = false)
    private BigDecimal avg_vol_3_months;

    @Column(name = "bid",nullable = false)
    private BigDecimal bid;

    @Column(name = "ask",nullable = false)
    private BigDecimal ask;

    @Column(name = "prev_close",nullable = false)
    private BigDecimal prevClose;

    @Column(name = "52_week_high",nullable = false)
    private BigDecimal week_high_52;

    @Column(name = "52_week_low",nullable = false)
    private BigDecimal week_low_52;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="symbol_id",
            foreignKey=@ForeignKey(name = "FK_SYMBOL_DAILY"))
    private Symbol symbol;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("dailyId:").append(this.dailyId)
                .append(",timestamp:").append(this.date)
                .append(",price:").append(this.price)
                .append(",open:").append(this.open)
                .append(",high:").append(this.high)
                .append(",low:").append(this.low)
                .append(",close:").append(this.close)
                .append(",volume:").append(this.volume)
                .append(",avg_vol_3_months:").append(this.avg_vol_3_months)
                .append(",bid:").append(this.bid)
                .append(",ask:").append(this.ask)
                .append(",prev_close:").append(this.prevClose)
                .append(",week_high_52:").append(this.week_high_52)
                .append(",week_low_52:").append(this.week_low_52)
                .append(",symbol:").append(this.symbol.getSymbol());
        return sb.toString();
    }

}
