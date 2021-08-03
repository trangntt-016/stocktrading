package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.DailyDtoBidAsk;
import com.canada.edu.stocktrading.dto.DailyDto03MSummary;
import com.canada.edu.stocktrading.dto.DailyDtoDetails;

import java.math.BigDecimal;
import java.util.List;


public interface DailyService {

    List<DailyDtoDetails> findAllDailiesBySymbolIds(List<Integer> symbolIds);

    DailyDto03MSummary findDailyBySymbolId(Integer symbolId);

    DailyDtoBidAsk getDailyBidAskBySymbolId(Integer symbolId);

    BigDecimal getMatchedPriceInNext15sBySymbolId(Integer symbolId);

}
