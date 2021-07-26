package com.canada.edu.stocktrading.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HistoricalQuoteDto {
    Date date;
    BigDecimal open;
    BigDecimal close;
    BigDecimal high;
    BigDecimal low;
}
