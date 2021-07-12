package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="symbol_id",
            foreignKey=@ForeignKey(name = "FK_SYMBOL_DAILY"))
    private Symbol symbol;

}
