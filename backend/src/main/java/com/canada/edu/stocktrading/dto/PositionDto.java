package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

enum TypesOfTrading {
    LONG,
    SHORT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {
    Symbol symbol;

    Integer quantity;

    BigDecimal lastPrice;

    BigDecimal marketValue;

    BigDecimal avgPrice;

    BigDecimal totalCost;

    BigDecimal unrealizedPL;

    BigDecimal unrealizedPLPercent;

    TypesOfTrading type;

    public void setType(){
        this.type =  (this.quantity > 0) ? TypesOfTrading.LONG : TypesOfTrading.SHORT;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PositionDto(symbolId=").append(this.symbol.getSymbolId())
                .append(",symbol=").append(this.symbol.getSymbol())
                .append(",name=").append(this.symbol.getName())
                .append(",filledQuantity=").append(this.quantity)
                .append(",lastPrice=").append(this.lastPrice)
                .append(",marketValue=").append(this.marketValue)
                .append(",avgPrice=").append(this.avgPrice)
                .append(",totalCost=").append(this.totalCost)
                .append(",unrealizedPL=").append(this.unrealizedPL)
                .append(",unrealizedPLPercent=").append(this.unrealizedPLPercent)
                .append(",type=").append(this.type.name());

        return sb.toString();
    }
}
