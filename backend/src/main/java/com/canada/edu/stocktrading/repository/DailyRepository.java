package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Integer>, CustomDailyRepository {
    List<Daily>findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds);

    @Query(value = "SELECT price FROM dailies WHERE timestamp <=:ts AND symbol_id =:symbolId ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    BigDecimal findCurrentPriceBySymbolId(Timestamp ts, Integer symbolId);

    @Query(value = "SELECT * FROM dailies WHERE HOUR(timestamp) =:hour and MINUTE(timestamp) =:minutes and SECOND(timestamp) <=:seconds AND symbol_id =:symbolId ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Daily findCurrentDailyBySymbolId(Integer hour, Integer minutes, Integer seconds, Integer symbolId);

    @Query(value = "SELECT * FROM dailies WHERE HOUR(timestamp) =:hour and MINUTE(timestamp) =:minutes and SECOND(timestamp) <=:seconds AND price =:limitPrice AND symbol_id =:symbolId  ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Daily findMatchedByLimitPriceAndSymbolId(Integer hour, Integer minutes, Integer seconds, BigDecimal limitPrice, Integer symbolId);

    @Query(value = "SELECT price FROM dailies WHERE HOUR(timestamp) =:hour and MINUTE(timestamp) =:minutes and SECOND(timestamp) <=:seconds AND symbol_id =:symbolId ORDER BY timestamp LIMIT 1", nativeQuery = true)
    BigDecimal findMatchedPriceNext15sBySymbolId(Integer hour, Integer minutes, Integer seconds, Integer symbolId);

    @Query(value = "SELECT * FROM dailies WHERE HOUR(timestamp) <=:hour and MINUTE(timestamp) <=:minutes and SECOND(timestamp) = 0 AND symbol_id =:symbolId", nativeQuery = true)
    List<Daily> findAllUntilPresentByTimestampAndSymbolId(Integer hour, Integer minutes, Integer symbolId);


}
