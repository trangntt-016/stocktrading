package com.canada.edu.stocktrading.service.dto;

import com.canada.edu.stocktrading.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyDto {
    private Integer dailyId;

    private Date timestamp;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigDecimal volume;

    private BigDecimal bid;

    private BigDecimal ask;

    private BigDecimal prevClose;

    private Symbol symbol;

    private BigDecimal change;

    private BigDecimal changeInPercent;
}
