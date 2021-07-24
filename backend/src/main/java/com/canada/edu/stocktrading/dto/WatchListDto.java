package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchListDto {
    private Integer watchlistId;
    private String name;
    private Set<Symbol> symbols;
}
