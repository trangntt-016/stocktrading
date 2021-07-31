package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String userId;

    private BigDecimal initialValue;

    private BigDecimal netAccountValue;

    private BigDecimal overallPL;

    private BigDecimal PLChange;

    private BigDecimal marketValue;

    private BigDecimal buyingPower;
}
