package com.canada.edu.stocktrading.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            foreignKey=@ForeignKey(name = "FK_USER_ORDER"))
    private UserEntity user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "symbol_id",
           foreignKey=@ForeignKey(name = "FK_SYMBOL_ORDER"))
   private Symbol symbol;

    @Enumerated(EnumType.STRING)
    @Column(name="order_side", nullable = false,length=10)
    private OrderSide orderSide;

    @Column(name="filled_quantity",nullable = false)
    private Integer filledQuantity;

    @Column(name="filled_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date filledTime;

    @Column(name="limit_price")
    private BigDecimal limitPrice;

    @Column(name="avg_price")
    private BigDecimal avgPrice;

    @Enumerated(EnumType.STRING)
    @Column(name="order_type", nullable = false, length=10)
    private OrderType orderType;

    @Column(name="order_placed",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderPlaced;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status", nullable=false, length=10)
    private OrderStatus orderStatus;

}


