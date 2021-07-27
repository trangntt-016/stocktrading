package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Integer>, CustomDailyRepository {
    List<Daily>findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds);

    @Query(value = "SELECT price FROM dailies WHERE timestamp <=:ts AND symbol_id =:symbolId ORDER BY timestamp LIMIT 1", nativeQuery = true)
    BigDecimal findCurrentPriceBySymbolId(Timestamp ts, Integer symbolId);

    @Query(value = "SELECT * FROM dailies WHERE timestamp <=:ts AND symbol_id =:symbolId ORDER BY timestamp LIMIT 1", nativeQuery = true)
    Daily findCurrentDailyBySymbolId(Timestamp ts, Integer symbolId);
}
