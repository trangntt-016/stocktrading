package com.canada.edu.stocktrading.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class SymbolDto {
    String symbol;
    String name;
}
