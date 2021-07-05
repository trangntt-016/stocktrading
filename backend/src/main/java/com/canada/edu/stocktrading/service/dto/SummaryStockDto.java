package com.canada.edu.stocktrading.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class SummaryStockDto {
    String symbol;
    BigDecimal currentPrice;
    BigDecimal change;
    BigDecimal changePercent;
}
