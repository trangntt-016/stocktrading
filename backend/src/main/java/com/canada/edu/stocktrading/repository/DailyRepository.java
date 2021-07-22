package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Integer>,CustomDailyRepository {
    List<Daily>findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds);
}
