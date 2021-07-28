package com.canada.edu.stocktrading.dto;

import com.canada.edu.stocktrading.model.OrderSide;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilledDto {
    private Integer orderId;

    private SymbolDto symbol;

    private OrderSide orderSide;

    private Integer filledQuantity;

    private Date filledTime;

    private BigDecimal limitPrice;

    private BigDecimal avgPrice;

    private OrderType orderType;

    private Date orderPlaced;

    private OrderStatus orderStatus;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("orderId:").append(this.orderId)
                .append(",").append(this.symbol.toString())
                .append(",").append(this.orderSide.toString())
                .append(",").append(this.filledQuantity)
                .append(",").append(this.filledTime)
                .append(",").append(this.limitPrice)
                .append(",").append(this.avgPrice)
                .append(",").append(this.orderType.toString())
                .append(",").append(this.orderPlaced)
                .append(",").append(this.orderStatus.toString());
        return sb.toString();
    }
}
