package com.canada.edu.stocktrading.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChartDto {
    String name;
    List<HistoricalQuoteDto>series;
}
