package com.canada.edu.stocktrading.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SymbolDto {
    String symbol;
    String name;
}
