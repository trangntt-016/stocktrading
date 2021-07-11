package com.canada.edu.stocktrading.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class ChartDto {
    String name;
    List<HistoricalQuoteDto>series;
}
