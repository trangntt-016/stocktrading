package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.Daily;

import java.sql.Timestamp;
import java.util.List;

public interface CustomDailyRepository {
    List<Daily> findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds);
}
