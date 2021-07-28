package com.canada.edu.stocktrading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolDto {
    String symbol;
    String name;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("symbol:").append(this.symbol)
                .append(",name:").append(this.name);
        return sb.toString();
    }
}
