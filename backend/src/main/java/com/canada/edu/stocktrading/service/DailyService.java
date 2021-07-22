package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.DailyDtoWith03MonthSummary;
import com.canada.edu.stocktrading.service.dto.DailyDetailsDto;

import java.util.List;


public interface DailyService {

    List<DailyDetailsDto> findAllDailiesBySymbolIds(List<Integer> symbolIds);

    DailyDtoWith03MonthSummary findDailyBySymbolId(Integer symbolId);

}
