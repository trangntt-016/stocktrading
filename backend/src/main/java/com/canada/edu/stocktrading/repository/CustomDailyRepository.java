package com.canada.edu.stocktrading.dao;

import com.canada.edu.stocktrading.model.Daily;

import java.sql.Timestamp;
import java.util.List;

public interface DailyDao {
    List<Daily> findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds);
}
