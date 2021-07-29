package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.DailyBidAskDto;
import com.canada.edu.stocktrading.dto.DailyDto03MSummary;
import com.canada.edu.stocktrading.dto.DailyDetailsDto;

import java.math.BigDecimal;
import java.util.List;


public interface DailyService {

    List<DailyDetailsDto> findAllDailiesBySymbolIds(List<Integer> symbolIds);

    DailyDto03MSummary findDailyBySymbolId(Integer symbolId);

    DailyBidAskDto getDailyBidAskBySymbolId(Integer symbolId);

    BigDecimal getMatchedPriceInNext15sBySymbolId(Integer symbolId);

}
