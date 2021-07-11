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
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "timestamp",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "open",nullable = false)
    private BigDecimal open;

    @Column(name = "close",nullable = false)
    private BigDecimal close;

    @Column(name = "high",nullable = false)
    private BigDecimal high;

    @Column(name = "low",nullable = false)
    private BigDecimal low;

    @Column(name = "volume",nullable = false)
    private BigDecimal volume;

    @Column(name = "symbol",nullable = false)
    private String symbol;
}
