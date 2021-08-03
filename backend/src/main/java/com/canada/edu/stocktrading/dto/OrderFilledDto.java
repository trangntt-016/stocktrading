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

    private String newlyFilled = "false";

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("OrderFilledDto(orderId=").append(this.orderId)
                .append(",symbol=").append(this.symbol.getSymbol())
                .append(",name=").append(this.symbol.getName())
                .append(",orderSide=").append(this.orderSide.name())
                .append(",filledQuantity=").append(this.filledQuantity)
                .append(",filledTime=").append(this.filledTime)
                .append(",limitPrice=").append(this.limitPrice)
                .append(",avgPrice=").append(this.avgPrice)
                .append(",orderType=").append(this.orderType.name())
                .append(",orderPlaced=").append(this.orderPlaced)
                .append(",orderStatus=").append(this.orderStatus.name())
                .append(",newlyFilled=").append(this.newlyFilled);

        return sb.toString();
    }

}
