package com.canada.edu.stocktrading.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SummaryStockDto {
    String symbol;
    BigDecimal currentPrice;
    BigDecimal change;
    BigDecimal changePercent;
}
